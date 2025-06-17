package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.CategoriaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoriaService {
    @Headers("Content-Type: application/json")
    @GET("categoria")
    suspend fun getCategorias(): Response<CategoriaResponse>
}