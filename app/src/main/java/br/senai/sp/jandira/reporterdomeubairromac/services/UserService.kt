package br.senai.sp.jandira.reporterdomeubairromac.services


import br.senai.sp.jandira.reporterdomeubairromac.model.LoginRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.LoginResponse
import br.senai.sp.jandira.reporterdomeubairromac.model.User
import br.senai.sp.jandira.reporterdomeubairromac.model.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @Headers("Content-Type: application/json")
    @POST("usuario")
    suspend fun registerUser(@Body user: UserRequest): retrofit2.Response<UserRequest>

    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}