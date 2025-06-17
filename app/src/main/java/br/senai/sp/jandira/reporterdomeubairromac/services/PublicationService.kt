package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.GetOcorrencia
import br.senai.sp.jandira.reporterdomeubairromac.model.MidiaRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.OccorenciaResponse
import br.senai.sp.jandira.reporterdomeubairromac.model.PostCreateResponse
import br.senai.sp.jandira.reporterdomeubairromac.model.PostRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface PublicationService {
    @Headers("Content-Type: application/json")
    @POST("ocorrencias")
    suspend fun enviarOcorrencia(
        @Body ocorrencia: PostRequest
    ): Response<PostCreateResponse>

    @POST("midias")
    @Headers("Content-Type: application/json")
    suspend fun enviarMidia(
        @Body midia: MidiaRequest
    ): Response<Unit>

//    @GET("ocorrencias")
//    suspend fun getOcorrencias(
//    ): Response<OccorenciaResponse>

    @GET("ocoreencias")
    fun listAll(): Call<OccorenciaResponse>

    @GET("ocorrencias/{id}")
    fun findById(@Path("id") id: Int): Call<GetOcorrencia>
}
