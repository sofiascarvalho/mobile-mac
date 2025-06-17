package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class MidiaResponse(
    @SerializedName("id_midia") val idMidia: Int?,
    @SerializedName("nome_arquivo") val nomeArquivo: String?,
    val url: String?,
    val tamanho: Long?, // Use Long? para tamanho, caso seja um número grande
    @SerializedName("id_ocorrencia") val idOcorrencia: Int?,
    @SerializedName("id_usuario") val idUsuario: Int?
)