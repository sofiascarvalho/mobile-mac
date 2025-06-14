package br.senai.sp.jandira.reporterdomeubairromac.model

data class Ocorrencia(
    val id_ocorrencia: Int,
    val titulo: String,
    val descricao: String,
    val data_criacao: String,
    val usuario: User,
    val id_categoria: Int,
    val id_status: Int,
    val id_endereco: Int
)