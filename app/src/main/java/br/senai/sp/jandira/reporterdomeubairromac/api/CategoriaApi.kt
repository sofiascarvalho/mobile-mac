package br.senai.sp.jandira.reporterdomeubairromac.api

import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria
import retrofit2.Response
import retrofit2.http.GET

interface CategoriaApi {
    @GET("categorias") // ou o endpoint correto
    suspend fun listarCategorias(): Response<List<Categoria>>
}
