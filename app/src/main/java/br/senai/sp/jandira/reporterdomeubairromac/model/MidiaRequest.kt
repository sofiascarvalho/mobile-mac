package br.senai.sp.jandira.reporterdomeubairromac.model

data class MidiaRequest(
    val nome_arquivo: String,
    val url: String,
    val tamanho: Int,
    val id_ocorrencia: Int,
    val id_usuario: Int
)
