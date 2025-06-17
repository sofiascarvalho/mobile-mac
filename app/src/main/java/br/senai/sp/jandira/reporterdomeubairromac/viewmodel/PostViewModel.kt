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
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitViaCep
import com.google.gson.Gson
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.time.LocalDate

class PostViewModel : ViewModel() {

    private val publicationService = RetrofitFactory.publicationService
    private val enderecoService = RetrofitFactory.enderecoService
    private val viaCepService = RetrofitViaCep.service

    val conteudo = mutableStateOf("")
    val categorias = mutableStateOf<List<Categoria>>(emptyList())
    val listaOcorrencias = MutableLiveData<List<GetOcorrencia>>(emptyList())
    val posts = mutableStateOf(listOf<Post>())

    // Buscar ou criar endereço
//    fun buscarOuCriarEndereco(cep: String, onResult: (Int?) -> Unit) {
//        viewModelScope.launch {
//            try {
//                val viaCepResponse = viaCepService.buscarCep(cep)
//                val viaCep = viaCepResponse.body() ?: run {
//                    onResult(null)
//                    return@launch
//                }
//
//                val enderecoParaBack = EnderecoRequest(
//                    logradouro = viaCep.logradouro ?: "",
//                    bairro = viaCep.bairro ?: "",
//                    cidade = viaCep.localidade ?: "",
//                    estado = viaCep.uf ?: "",
//                    cep = viaCep.cep ?: "",
//                    latitude = null,
//                    longitude = null
//                )
//
//                val response = enderecoService.criarOuObterEndereco(enderecoParaBack)
//
//                if (response.isSuccessful) {
//                    val enderecoInserido = response.body()?.enderecos?.firstOrNull()
//                    onResult(enderecoInserido?.id_endereco)
//                } else {
//                    Log.e("Endereço", "Erro backend: ${response.code()}")
//                    onResult(null)
//                }
//
//            } catch (e: Exception) {
//                Log.e("Endereço", "Erro ao buscar/criar endereço", e)
//                onResult(null)
//            }
//        }
//    }

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

                val json = Gson().toJson(ocorrenciaRequest)
                Log.d("PostViewModel", "JSON enviado: $json")

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
        idOcorrencia: Int,
        idUsuario: Int,
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
                println("Erro ao enviar mídia $index")
                println("Status Code: ${response.code()}")
                println("Body de erro: ${response.errorBody()?.string()}")
                onError("Falha ao enviar mídia $index")
                throw Exception("Erro no envio de mídia")
            }
        }
    }

    private fun obterIdCategoria(nomeCategoria: String): Int {
        return categorias.value.firstOrNull { it.nome_categoria == nomeCategoria }?.id_categoria ?: 0
    }
}

