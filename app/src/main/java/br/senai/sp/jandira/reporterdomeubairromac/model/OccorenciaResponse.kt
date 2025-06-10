// br.senai.sp.jandira.reporterdomeubairromac.model/OccorenciaResponse.kt
package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class OccorenciaResponse(
    val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    val itens: Int,
    val ocorrencias: List<GetOcorrencia> // <-- Aqui, deve ser uma lista da Ocorrência que você *recebe* do GET
)