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

    fun publicar() {
        val texto = conteudo.value.trim()
        if (texto.isBlank()) return

        viewModelScope.launch {
            try {
                publicationService.createOccurrence(PostRequest(texto))
                conteudo.value = ""
                carregarPosts()
            } catch (e: Exception) {
                // lidar com erro
                e.printStackTrace()
            }
        }
    }
}
