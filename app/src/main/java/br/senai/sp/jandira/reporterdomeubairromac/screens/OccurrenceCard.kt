package br.senai.sp.jandira.reporterdomeubairromac.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria

@Composable
fun OccurrenceCard(
    titulo: String = "Titulo da Ocorrência - ",
    descricao: String = "Descrição da Ocorrência",
    nome_status: String = "Status",
    nome_categoria: String = "Categoria",
    nome: String = "Nome Usuario",
    logradouro: String = "Logradouro, ",
    cidade: String = "Cidade - ",
    estado: String = "Estado"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(380.dp),
        //colors = CardDefaults.cardColors(contentColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = nome, color = androidx.compose.ui.graphics.Color.Black)
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = logradouro, color = androidx.compose.ui.graphics.Color.Black)
                Text(text = cidade, color = androidx.compose.ui.graphics.Color.Black)
                Text(text = estado, color = androidx.compose.ui.graphics.Color.Black)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(200.dp)
                .background(Color.Black)
        ) {

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "",
                tint = Color(0xFF000000)
            )
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = "",
                tint = Color(0xFF000000)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ){
            Row {
                Text(text = titulo,
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 17.sp)
                Text(text = nome_categoria,
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 17.sp)
            }
            Text(text = descricao,
                color = androidx.compose.ui.graphics.Color.Black,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OccurrenceCardPreview() {
    OccurrenceCard()
}