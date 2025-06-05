package br.senai.sp.jandira.reporterdomeubairromac.model

data class OccorenciaResponse(
    val id_ocorrencia: Int,
    val titulo: String,
    val descricao: String,
    val data_criacao: String,
    val usuario: List<UsuarioResponse>
)

