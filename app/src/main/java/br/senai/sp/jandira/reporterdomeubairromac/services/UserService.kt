package br.senai.sp.jandira.reporterdomeubairromac.services


import br.senai.sp.jandira.reporterdomeubairromac.model.LoginRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.User
import br.senai.sp.jandira.reporterdomeubairromac.model.UserRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @Headers("Content-Type: application/json")
    @POST("usuario")
    fun registerUser(@Body user: UserRequest): retrofit2.Call<UserRequest>

    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): retrofit2.Call<User>

}