package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id_usuario")
    val idUsuario: Int,
    val nome: String,
    val email: String,
    val senha: String
)
