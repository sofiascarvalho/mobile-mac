package br.senai.sp.jandira.reporterdomeubairromac.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria
import br.senai.sp.jandira.reporterdomeubairromac.model.Post
import br.senai.sp.jandira.reporterdomeubairromac.services.CategoriaService
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDate

class PostViewModel : ViewModel() {

    private val publicationService = RetrofitFactory.publicationService

    var posts = mutableStateOf(listOf<Post>())
        private set

    val conteudo = mutableStateOf("")

    val categorias = mutableStateOf(listOf<Categoria>())



    private fun obterIdCategoria(nome: String): Int {
        return when (nome) {
            "Assalto" -> 1
            "Incêndio" -> 2
            "Acidente" -> 3
            "Obra irregular" -> 4
            else -> 0 // ou lançar erro
        }
    }


    fun carregarCategorias() {
        viewModelScope.launch {
            try {
                val response = CategoriaService. // ou o nome correto
                if (response.isSuccessful) {
                    categorias.value = response.body() ?: emptyList()
                } else {
                    Log.e("ViewModel", "Erro na resposta: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Erro ao carregar categorias", e)
            }
            Log.d("ViewModel", "Categorias carregadas: ${categorias.value}")

        }
    }



    /*
    fun carregarCategorias() {
        viewModelScope.launch {
            val response = publicationService.getCategorias()
            if (response.isSuccessful) {
                categorias.value = response.body()?.categorias ?: emptyList()
            } else {
                categorias.value = emptyList()
            }
        }
    }
     */


    /**
     * Publica uma ocorrência com imagens.
     *
     * @param titulo título da ocorrência
     * @param categoriaSelecionada nome da categoria selecionada
     * @param imagensUri lista de URIs das imagens para upload
     * @param context contexto para manipulação de arquivos
     * @param idUsuario id do usuário que está publicando
     * @param idEndereco id do endereço relacionado à ocorrência
     * @param onSuccess callback em caso de sucesso
     * @param onError callback em caso de erro, com mensagem
     */
    fun publicar(
        titulo: String,
        categoriaSelecionada: String,
        imagensUri: List<Uri>,
        context: Context,
        idUsuario: Int,
        idEndereco: Int,
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
                    "id_usuario" to idUsuario,
                    "id_endereco" to idEndereco,
                    "id_categoria" to obterIdCategoria(categoriaSelecionada),
                    "id_status" to 1
                )

                val responseOcorrencia = publicationService.enviarOcorrencia(ocorrenciaRequest)

                if (!responseOcorrencia.isSuccessful) {
                    onError("Erro ao registrar ocorrência: ${responseOcorrencia.message()}")
                    return@launch
                }

                val idOcorrencia = responseOcorrencia.body()?.result?.get(0)?.id_ocorrencia
                    ?: return@launch onError("ID da ocorrência não retornado")

                // 2. Upload das imagens no Azure Blob (todas)
                if (imagensUri.isNotEmpty()) {
                    val sasToken = "<sp=rwd&st=2025-06-02T01:10:28Z&se=2025-07-01T09:10:28Z&sv=2024-11-04&sr=c&sig=1%2BCu0taa%2F8a4GMPdZKZlGIItkBXWK2c4lFPRGUEaiTQ%3D>" // ⛔ Substitua com seu SAS real
                    val blobBaseUrl = "https://ocorrenciasimagens.blob.core.windows.net/imagens"
                    val client = OkHttpClient()

                    for (uri in imagensUri) {
                        val file = uriToFile(context, uri)
                        val nomeArquivo = "${System.currentTimeMillis()}_${file.name}"
                        val blobUploadUrl = "$blobBaseUrl/$nomeArquivo?$sasToken"

                        val request = Request.Builder()
                            .url(blobUploadUrl)
                            .put(file.asRequestBody("image/*".toMediaTypeOrNull()))
                            .addHeader("x-ms-blob-type", "BlockBlob")
                            .build()

                        val azureResponse = client.newCall(request).execute()

                        if (!azureResponse.isSuccessful) {
                            onError("Erro ao subir imagem no Azure: ${azureResponse.message}")
                            file.delete()
                            return@launch
                        }

                        // 3. Enviar dados da mídia para o backend
                        val midiaRequest = mapOf(
                            "nome_arquivo" to nomeArquivo,
                            "url" to blobUploadUrl,
                            "tamanho" to file.length(),
                            "id_ocorrencia" to idOcorrencia,
                            "id_usuario" to idUsuario
                        )

                        val responseMidia = publicationService.enviarMidia(midiaRequest)

                        if (!responseMidia.isSuccessful) {
                            onError("Erro ao salvar imagem no banco.")
                            file.delete()
                            return@launch
                        }

                        // Deleta arquivo temporário
                        file.delete()
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
