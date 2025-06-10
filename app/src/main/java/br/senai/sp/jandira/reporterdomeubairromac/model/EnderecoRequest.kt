package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class EnderecoRequest(
    //val logradouro: String,
    //val bairro: String,
    //val cidade: String,
    //val estado: String,
    //val cep: String,
    //val latitude: Float,
    //val longitude: Float

    @SerializedName("id_endereco") val idEndereco: Int?,
    val logradouro: String?,
    val bairro: String?,
    val cidade: String?,
    val estado: String?,
    val cep: String?,
    val longitude: Double?, // <-- No seu JSON, longitude é STRING
    val latitude: Double?
)
