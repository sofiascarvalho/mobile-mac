package br.senai.sp.jandira.reporterdomeubairromac.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.reporterdomeubairromac.model.Post
import br.senai.sp.jandira.reporterdomeubairromac.model.PostRequest
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val publicationService = RetrofitFactory.publicationService

    var posts = mutableStateOf(listOf<Post>())
        private set

    var conteudo = mutableStateOf("")

    init {
        carregarPosts()
    }

    fun carregarPosts() {
        viewModelScope.launch {
            try {
                val response = publicationService.getOcorrencias()
                posts.value = response.reversed() // ✅ CORRIGIDO AQUI
            } catch (e: Exception) {
                // lidar com erro (ex: Log.e(...))
                e.printStackTrace()
            }
        }
    }

    fun publicar(
        categoriaSelecionada:String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val texto =conteudo.value.trim()
        if (texto.isBlank()){
            onError("Descrição vazia!")
            return
        }

        val categoriaMap = mapOf(
            "Assalto" to 1,
            "Incêndio" to 2,
            "Acidente" to 3,
            "Assalto" to 4
        )

        val idCategoria = categoriaMap[categoriaSelecionada] ?:1

        val request = PostRequest(
            descricao = texto,
            id_categoria = idCategoria,
            id_usuario = 1,
            id_status = 1
        )

        viewModelScope.launch {
            try {
                val response = publicationService.createOccurrence(request)
                conteudo.value=""
                carregarPosts()
                onSuccess()
            }catch (e: Exception) {
                e.printStackTrace()
                onError("Erro ao publicar: ${e.message}")
            }
        }
    }
}
