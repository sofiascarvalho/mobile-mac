package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.gson.annotations.SerializedName

data class PostCreateResponse(
    val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    val message: String, // O campo "message" que a API retorna
    val result: List<PostCreateResult>)
