package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.Post
import br.senai.sp.jandira.reporterdomeubairromac.model.PostRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PublicationService {
    @GET("ocorrencias")
    suspend fun getOcorrencias(): List<Post>

    @POST("ocorrencias")
    suspend fun createOccurrence(@Body post: PostRequest): Response<Unit>
}