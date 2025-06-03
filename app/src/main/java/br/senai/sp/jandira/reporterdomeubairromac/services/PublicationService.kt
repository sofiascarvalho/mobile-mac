package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria
import br.senai.sp.jandira.reporterdomeubairromac.model.CategoriaRespose
import br.senai.sp.jandira.reporterdomeubairromac.model.Post
import br.senai.sp.jandira.reporterdomeubairromac.model.PostRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.ResponsePadrao
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PublicationService {
    @POST("ocorrencias")
    suspend fun enviarOcorrencia(
        @Body ocorrencia: Map<String, @JvmSuppressWildcards Any>
    ): Response<Post>

    @POST("midias")
    suspend fun enviarMidia(
        @Body midia: Map<String, @JvmSuppressWildcards Any>
    ): Response<ResponsePadrao>

    @GET("categorias") // ou o endpoint correto
    suspend fun getCategorias(): Response<CategoriaRespose>
}