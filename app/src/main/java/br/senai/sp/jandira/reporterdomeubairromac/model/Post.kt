package br.senai.sp.jandira.reporterdomeubairromac.model

data class Post(
    val result: List<Ocorrencia>
)

data class Ocorrencia(
    val id_ocorrencia: Int,
    val titulo: String,
    val descricao: String,
    // Adicione outros campos conforme o JSON retornado
)