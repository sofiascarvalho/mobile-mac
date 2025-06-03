package br.senai.sp.jandira.reporterdomeubairromac.model

data class ResponsePadrao(
    val status_code: Int,
    val result: List<Map<String, Any>>? = null,
    val message: String? = null
)
