package br.senai.sp.jandira.reporterdomeubairromac.model

data class LoginResponse(
    val status: Boolean,
    val status_code: Int,
    val itens: Int,
    val users: List<User>
)
