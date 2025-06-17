// src/main/java/br/senai/sp/jandira/reporterdomeubairromac/viewmodel/OccurrenceViewModel.kt (ou o seu pacote de viewmodels)

package br.senai.sp.jandira.reporterdomeubairromac.viewmodel // Ajuste para o seu pacote correto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.reporterdomeubairromac.model.GetOcorrencia
import br.senai.sp.jandira.reporterdomeubairromac.model.OccorenciaResponse
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OccurrenceViewModel : ViewModel() {

    private val _occurrenceList = MutableStateFlow<List<GetOcorrencia>>(emptyList())
    val occurrenceList: StateFlow<List<GetOcorrencia>> = _occurrenceList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchOccurrences()
    }

    fun fetchOccurrences() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val callOccurrence = RetrofitFactory
                .getOccurrenceService()
                .listAll()

            callOccurrence.enqueue(object : Callback<OccorenciaResponse> {
                override fun onResponse(
                    call: Call<OccorenciaResponse>,
                    response: Response<OccorenciaResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {

                        _occurrenceList.value = response.body()?.ocorrencias ?: emptyList()
                    } else {

                        _errorMessage.value = "Erro na resposta: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<OccorenciaResponse>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = "Erro de rede: ${t.message}"
                    t.printStackTrace()
                }
            })
        }
    }
}