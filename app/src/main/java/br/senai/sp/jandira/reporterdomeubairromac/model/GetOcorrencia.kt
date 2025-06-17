package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.android.gms.common.api.Status
import com.google.gson.annotations.SerializedName

data class GetOcorrencia(
    @SerializedName("id_ocorrencia") val idOcorrencia: Int?,
    val titulo: String?,
    val descricao: String?,
    @SerializedName("data_criacao") val dataCriacao: String?,
    val usuario: List<User>?,
    val stat: List<StatusResponse>?,
    val categoria: List<CategoriaResponse>?,
    val endereco: List<EnderecoResponse>?,
    val midia: List<MidiaResponse>?
)