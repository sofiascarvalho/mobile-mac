package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class PostRequest(
    val titulo: String,
    val descricao: String,
    @SerializedName("data_criacao") val dataCriacao: String,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_categoria") val idCategoria: Int,
    @SerializedName("id_status") val idStatus: Int,
    @SerializedName("id_endereco") val idEndereco: Int
)
