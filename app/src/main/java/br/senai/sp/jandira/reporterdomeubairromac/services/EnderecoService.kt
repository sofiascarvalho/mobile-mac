package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.EnderecoRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.EnderecoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface EnderecoService {

    @Headers("Content-Type: application/json")
    @POST("endereco")
    suspend fun criarOuObterEndereco(
        @Body endereco: EnderecoRequest
    ): Response<EnderecoResponse>
}
