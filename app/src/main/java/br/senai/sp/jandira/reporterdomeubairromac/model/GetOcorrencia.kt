package br.senai.sp.jandira.reporterdomeubairromac.model

import com.google.android.gms.common.api.Status
import com.google.gson.annotations.SerializedName

data class GetOcorrencia(
    val idOcorrencia: Int, // ID da ocorrência recebida
    val titulo: String?,
    val descricao: String?,
    val dataCriacao: String?,
    val usuario: List<User>?,
    val stat: List<Status>?,
    val categoria: List<Categoria>?,
    val endereco: List<EnderecoRequest>?, // Lista de Endereços (do GET)
    val midia: List<MidiaRequest>?
)
