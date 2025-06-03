package br.senai.sp.jandira.reporterdomeubairromac.services

import br.senai.sp.jandira.reporterdomeubairromac.api.CategoriaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private val BASE_URL = "http://10.0.2.2:8080/v1/controle-usuario/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService: UserService = retrofitFactory.create(UserService::class.java)
    val publicationService: PublicationService =retrofitFactory.create(PublicationService::class.java)
    val categoriaApi: CategoriaApi = retrofitFactory.create(CategoriaApi::class.java)
}


object RetrofitViaCep {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ViaCepService = retrofit.create(ViaCepService::class.java)
}
