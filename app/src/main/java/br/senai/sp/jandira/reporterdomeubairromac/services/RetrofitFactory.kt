package br.senai.sp.jandira.reporterdomeubairromac.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val BASE_URL = "http://10.0.2.2:8080/v1/controle-usuario/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUserService(): UserService {
        return retrofitFactory.create(UserService::class.java)
    }
}