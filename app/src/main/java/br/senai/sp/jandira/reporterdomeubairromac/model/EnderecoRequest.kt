package br.senai.sp.jandira.reporterdomeubairromac.model

data class EnderecoRequest(
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val cep: String,
    val latitude: Float,
    val longitude: Float
)
