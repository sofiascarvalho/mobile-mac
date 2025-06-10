package br.senai.sp.jandira.reporterdomeubairromac.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import br.senai.sp.jandira.reporterdomeubairromac.model.*
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import br.senai.sp.jandira.reporterdomeubairromac.model.MidiaRequest
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.time.LocalDate

class PostViewModel : ViewModel() {

    private val publicationService = RetrofitFactory.publicationService

    // Estados
    val conteudo = mutableStateOf("")
    val categorias = mutableStateOf<List<Categoria>>(emptyList())
    val listaOcorrencias = MutableLiveData<List<GetOcorrencia>>(emptyList())
    val posts = mutableStateOf(listOf<Post>())

    // Métodos de Categorias
    fun getCategorias() {
        viewModelScope.launch {
            try {
                val response = RetrofitFactory.categoriaService.getCategorias()
                if (response.isSuccessful) {
                    categorias.value = response.body()?.categorias ?: emptyList()
                } else {
                    Log.e("API", "Erro ao buscar categorias: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Falha ao carregar categorias", e)
            }
        }
    }

    // Métodos de Ocorrências
    fun getOcorrencias() {
        viewModelScope.launch {
            try {
                val response = publicationService.getOcorrencias()
                if (response.isSuccessful) {
                    listaOcorrencias.postValue(response.body()?.ocorrencias ?: emptyList())
                } else {
                    Log.e("API", "Erro ao buscar ocorrências: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Falha ao buscar ocorrências", e)
            }
        }
    }

    // Publicação de Ocorrência
    fun publicar(
        titulo: String,
        categoriaSelecionada: String,
        imagensUrl: List<String>,
        idUsuario: Int,
        idEndereco: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val idCategoria = obterIdCategoria(categoriaSelecionada).takeIf { it != 0 }
                    ?: run {
                        onError("Categoria inválida")
                        return@launch
                    }

                val ocorrenciaRequest = PostRequest(
                    titulo = titulo,
                    descricao = conteudo.value,
                    dataCriacao = LocalDate.now().toString(),
                    idUsuario = idUsuario,
                    idCategoria = idCategoria,
                    idStatus = 1,
                    idEndereco = idEndereco
                )

                val response = publicationService.enviarOcorrencia(ocorrenciaRequest)

                if (!response.isSuccessful) {
                    onError("Erro ao enviar ocorrência: ${response.code()}")
                    return@launch
                }

                val idOcorrencia = response.body()?.result?.firstOrNull()?.idOcorrencia
                    ?: run {
                        onError("ID da ocorrência não encontrado")
                        return@launch
                    }

                enviarMidias(imagensUrl, idOcorrencia, idUsuario, onError)
                onSuccess()

            } catch (e: Exception) {
                onError("Erro: ${e.message}")
                Log.e("PostViewModel", "Erro ao publicar", e)
            }
        }
    }

    private suspend fun enviarMidias(
        urls: List<String>,
        idOcorrencia: Int,  // Nome correto do parâmetro
        idUsuario: Int,     // Nome correto do parâmetro
        onError: (String) -> Unit
    ) {
        urls.forEachIndexed { index, url ->
            val midiaRequest = MidiaRequest(
                nome_arquivo = "imagem_${System.currentTimeMillis()}_$index.jpg",
                url = url,
                tamanho = 1_000_000,
                id_ocorrencia = idOcorrencia,
                id_usuario = idUsuario
            )

            val response = publicationService.enviarMidia(midiaRequest)
            if (!response.isSuccessful) {
                onError("Falha ao enviar mídia $index")
                throw Exception("Erro no envio de mídia")
            }
        }
    }

    // Upload de imagens para Azure (versão corrigida)
    fun uploadImagensAzure(
        context: Context,
        imagensUri: List<Uri>,
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val urls = mutableListOf<String>()
                val client = OkHttpClient()
                val blobUrlBase = "https://ocorrenciasimagens.blob.core.windows.net/imagens"
                val sasToken = "seu_token_aqui"  // Substitua pelo token real

                imagensUri.forEachIndexed { index, uri ->
                    val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                        ?: throw Exception("Erro ao ler imagem")

                    val fileName = "img_${System.currentTimeMillis()}_$index.jpg"
                    val uploadUrl = "$blobUrlBase/$fileName?$sasToken"

                    val request = Request.Builder()
                        .url(uploadUrl)
                        .put(bytes.toRequestBody("image/jpeg".toMediaTypeOrNull()))
                        .addHeader("x-ms-blob-type", "BlockBlob")
                        .build()

                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) throw Exception("Falha no upload")
                        urls.add("$blobUrlBase/$fileName")
                    }
                }

                onSuccess(urls)
            } catch (e: Exception) {
                onError("Upload falhou: ${e.message}")
            }
        }
    }

    private fun obterIdCategoria(nomeCategoria: String): Int {
        return categorias.value.firstOrNull { it.nome_categoria == nomeCategoria }?.id_categoria
            ?: 0
    }
}
