package br.senai.sp.jandira.reporterdomeubairromac.model

data class PostRequest(
    val descricao:String,
    val id_categoria:Int,
    val id_usuario:Int,
    val id_status:Int
)
