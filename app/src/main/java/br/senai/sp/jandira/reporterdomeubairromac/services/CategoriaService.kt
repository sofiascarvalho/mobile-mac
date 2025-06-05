package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.CategoriaRespose
import retrofit2.Response
import retrofit2.http.GET

interface CategoriaService {
    @GET("categoria")
    suspend fun getCategorias(): Response<CategoriaRespose>
}