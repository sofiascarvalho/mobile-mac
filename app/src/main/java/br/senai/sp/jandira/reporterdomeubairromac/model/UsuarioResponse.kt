package br.senai.sp.jandira.reporterdomeubairromac.model

data class UsuarioResponse(
    val id_usuario: Int,
    val nome: String,
    val email: String,
    val senha: String
)
