package br.senai.sp.jandira.reporterdomeubairromac.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.reporterdomeubairromac.model.Post
import br.senai.sp.jandira.reporterdomeubairromac.model.PostRequest
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDate

class PostViewModel : ViewModel() {

    private val publicationService = RetrofitFactory.publicationService

    var posts = mutableStateOf(listOf<Post>())
        private set

    val conteudo = mutableStateOf("")



    fun publicar(
        titulo: String,
        categoriaSelecionada: String,
        imagensUri: List<Uri>,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // 1. Montar dados da ocorrência (JSON puro)
                val ocorrenciaRequest = mapOf(
                    "titulo" to titulo,
                    "descricao" to conteudo.value,
                    "data_criacao" to LocalDate.now().toString(),
                    "id_usuario" to 1,  // <- ajuste para pegar ID do usuário real
                    "id_endereco" to 1, // <- ajuste para ID de endereço real
                    "id_categoria" to obterIdCategoria(categoriaSelecionada),
                    "id_status" to 1
                )

                val responseOcorrencia = RetrofitFactory.publicationService.enviarOcorrencia(ocorrenciaRequest)

                if (!responseOcorrencia.isSuccessful) {
                    onError("Erro ao registrar ocorrência: ${responseOcorrencia.message()}")
                    return@launch
                }

                val idOcorrencia = responseOcorrencia.body()?.result?.get(0)?.id_ocorrencia
                    ?: return@launch onError("ID da ocorrência não retornado")

                // 2. Upload da imagem no Azure Blob
                if (imagensUri.isNotEmpty()) {
                    val uri = imagensUri[0] // supondo uma imagem apenas
                    val file = uriToFile(context, uri)
                    val nomeArquivo = "${System.currentTimeMillis()}_${file.name}"
                    val sasToken = "<seu-token-sas>" // ⛔ Substitua com seu SAS real
                    val blobBaseUrl = "https://ocorrenciasimagens.blob.core.windows.net/imagens"
                    val blobUploadUrl = "$blobBaseUrl/$nomeArquivo?$sasToken"

                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url(blobUploadUrl)
                        .put(file.asRequestBody("image/*".toMediaTypeOrNull()))
                        .addHeader("x-ms-blob-type", "BlockBlob")
                        .build()

                    val azureResponse = client.newCall(request).execute()

                    if (!azureResponse.isSuccessful) {
                        return@launch onError("Erro ao subir imagem no Azure: ${azureResponse.message}")
                    }

                    // 3. Enviar dados da mídia para o backend
                    val midiaRequest = mapOf(
                        "nome_arquivo" to nomeArquivo,
                        "url" to blobUploadUrl,
                        "tamanho" to file.length(),
                        "id_ocorrencia" to idOcorrencia,
                        "id_usuario" to 1 // ajuste conforme necessário
                    )

                    val responseMidia = RetrofitFactory.publicationService.enviarMidia(midiaRequest)

                    if (!responseMidia.isSuccessful) {
                        onError("Erro ao salvar imagem no banco.")
                        return@launch
                    }
                }

                onSuccess()

            } catch (e: Exception) {
                onError("Erro inesperado: ${e.localizedMessage}")
            }
        }
    }


    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
        return tempFile
    }
}
