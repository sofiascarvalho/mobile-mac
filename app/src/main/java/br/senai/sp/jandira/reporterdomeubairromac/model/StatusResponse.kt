package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("id_status") val idStatus: Int?,
    @SerializedName("nome_status") val nomeStatus: String?
)