package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class EnderecoResponse(
    val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    val itens: Int,
    @SerializedName("enderecos") val enderecos: List<Endereco>
)

data class Endereco(
    @SerializedName("id_endereco") val idEndereco: Int?,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val cep: String,
    val longitude: String?,
    val latitude: String?
)

