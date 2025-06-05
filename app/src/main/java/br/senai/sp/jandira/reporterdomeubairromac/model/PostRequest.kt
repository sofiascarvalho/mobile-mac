package br.senai.sp.jandira.reporterdomeubairromac.model

data class PostRequest(
    val titulo:String,
    val descricao:String,
    val data_criacao:String,
    val id_usuario:Int,
    val id_categoria:Int,
    val id_status:Int,
    val id_endereco: Int,
)
