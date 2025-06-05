package br.senai.sp.jandira.reporterdomeubairromac.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria
import br.senai.sp.jandira.reporterdomeubairromac.model.Post
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream


class PostViewModel : ViewModel() {

    private val publicationService = RetrofitFactory.publicationService

    // Lista de posts (se você precisar exibir um feed, por exemplo)
    var posts = mutableStateOf(listOf<Post>())
        private set

    // Conteúdo (descrição) de uma nova ocorrência
    val conteudo = mutableStateOf("")

    // Lista de categorias obtida da API
    val categorias = mutableStateOf<List<Categoria>>(listOf())

    // ------------------------------------------------------------
    // MÉTODOS DE CATEGORIAS
    // ------------------------------------------------------------



    fun getCategorias() {
        viewModelScope.launch {
            try {
                val response = RetrofitFactory.categoriaService.getCategorias()
                if (response.isSuccessful) {
                    // A API retorna um objeto que encapsula a lista em "categorias"
                    categorias.value = response.body()?.categorias ?: emptyList()
                } else {
                    categorias.value = emptyList()
                    Log.e("API", "Erro ao buscar categorias: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Falha ao carregar categorias", e)
                categorias.value = emptyList()
            }
        }
    }

    /**
     * Converte o nome da categoria (string) no ID correspondente.
     * Ajuste esse mapeamento conforme as IDs reais do seu banco de dados.
     */
    private fun obterIdCategoria(nomeCategoria: String): Int {
        return when (nomeCategoria) {
            "Assalto"         -> 1
            "Incêndio"       -> 2
            "Acidente"        -> 3
            "Obra irregular" -> 4
            else              -> 0 // ou lance uma exceção, se preferir
        }
    }

    // ------------------------------------------------------------
    // UPLOAD DE IMAGENS NO FIREBASE STORAGE
    // ------------------------------------------------------------

    /**
     * Recebe uma lista de URIs locais (imagens do dispositivo), faz o upload para o Firebase Storage
     * e retorna, via callback, a lista de URLs públicas geradas.
     *
     * Observação: o FirebaseApp deve ter sido inicializado previamente (ver classe Application).
     */
    fun uploadImagensAzure(
        context: Context,
        imagensUri: List<Uri>,
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        val blobUrlBase = "https://ocorrenciasimagens.blob.core.windows.net/imagens"
        val sasToken = "sp=rwd&st=2025-06-02T01:10:28Z&se=2025-07-01T09:10:28Z&sv=2024-11-04&sr=c&sig=1%2BCu0taa%2F8a4GMPdZKZlGIItkBXWK2c4lFPRGUEaiTQ%3D"

        val client = OkHttpClient()
        val listaUrls = mutableListOf<String>()

        viewModelScope.launch {
            try {
                for ((index, uri) in imagensUri.withIndex()) {
                    // Lê bytes da Uri
                    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                    val bytes = inputStream?.readBytes()
                    inputStream?.close()

                    if (bytes == null) {
                        onError("Erro ao ler arquivo da URI")
                        return@launch
                    }

                    val fileName = "img_${System.currentTimeMillis()}_$index.jpg"
                    val uploadUrl = "$blobUrlBase/$fileName?$sasToken"

                    val mediaType = "image/jpeg".toMediaTypeOrNull()
                    val body = bytes.toRequestBody(mediaType)

                    val request = Request.Builder()
                        .url(uploadUrl)
                        .put(body)
                        .addHeader("x-ms-blob-type", "BlockBlob")
                        .build()

                    val response = client.newCall(request).execute()

                    if (!response.isSuccessful) {
                        onError("Falha ao enviar imagem $fileName: ${response.code}")
                        return@launch
                    }

                    listaUrls.add("$blobUrlBase/$fileName")
                }

                onSuccess(listaUrls)
            } catch (e: Exception) {
                onError("Erro ao fazer upload das imagens: ${e.message}")
            }
        }
    }


    // ------------------------------------------------------------
    // PUBLICAR OCORRÊNCIA
    // ------------------------------------------------------------

    /**
     * Envia uma nova ocorrência para a API, incluindo título, descrição, categoria e lista de URLs de imagens.
     *
     * @param titulo Título da ocorrência
     * @param categoriaSelecionada Nome da categoria selecionada (string)
     * @param imagensUrl Lista de URLs (String) das imagens já hospedadas em Storage
     * @param context Contexto para qualquer operação necessária (normalmente não usado aqui)
     * @param idUsuario ID do usuário que está criando a ocorrência
     * @param idEndereco ID do endereço vinculado (você deve criar/selecionar antes)
     * @param onSuccess Callback chamado quando o envio for bem-sucedido
     * @param onError Callback chamado caso ocorra algum erro, recebendo a mensagem de erro
     */
    fun publicar(
        titulo: String,
        categoriaSelecionada: String,
        imagensUrl: List<String>,
        context: Context,
        idUsuario: Int,
        idEndereco: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Monta um Map<String, Any> exatamente como a API espera
                val ocorrenciaMap = mapOf(
                    "titulo"     to titulo,
                    "descricao"  to conteudo.value,
                    "categoria"  to categoriaSelecionada,
                    "imagens"    to imagensUrl,
                    "id_usuario" to idUsuario,
                    "id_endereco" to idEndereco
                )

                val response = publicationService.enviarOcorrencia(ocorrenciaMap)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Erro ao enviar ocorrência: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Falha na conexão: ${e.message}")
            }
        }
    }

    // ------------------------------------------------------------
    // FUNÇÃO AUXILIAR (CASO PRECISE TRANSFORMAR URI EM File)
    // ------------------------------------------------------------

    /**
     * Converte um URI em um File temporário dentro do cache.
     * Está à disposição caso você precise manipular o arquivo antes de fazer upload
     * (mas, no exemplo atual, não é usado, já que usamos putFile(uri) diretamente).
     */
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
