package br.senai.sp.jandira.reporterdomeubairromac.model

data class EnderecoViaCep(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?
)
