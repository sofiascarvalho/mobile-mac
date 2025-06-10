package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.EnderecoViaCep
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

interface ViaCepService {
    @Headers("Content-Type: application/json")
    @GET("{cep}/json/")
    suspend fun buscarEndereco(@Path("cep") cep: String): EnderecoViaCep
}
