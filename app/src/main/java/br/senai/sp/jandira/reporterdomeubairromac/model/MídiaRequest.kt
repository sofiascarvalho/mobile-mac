package br.senai.sp.jandira.reporterdomeubairromac.model

data class MídiaRequest(
    val nome_arquivo: String,
    val url: String,
    val tamanho: Long,
    val id_ocorrencia: Int,
    val id_usuario: Int
)
