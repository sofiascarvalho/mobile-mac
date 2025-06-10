package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria
import br.senai.sp.jandira.reporterdomeubairromac.model.CategoriaRespose
import br.senai.sp.jandira.reporterdomeubairromac.model.MídiaRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.OccorenciaResponse
import br.senai.sp.jandira.reporterdomeubairromac.model.Ocorrencia
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
import java.util.Optional

interface PublicationService {
    @POST("ocorrencias")
    suspend fun enviarOcorrencia(
        @Body ocorrencia: PostRequest
    ): Response<Ocorrencia>

    @POST("midias")
    suspend fun enviarMidia(
        @Body midia: MídiaRequest
    ): Response<Unit>

    @GET("ocorrencias")
    suspend fun getOcorrencias(): Response<OccorenciaResponse>

}
