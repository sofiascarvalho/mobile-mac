package br.senai.sp.jandira.reporterdomeubairromac.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel

@Composable
fun OccurrenceScreen(navegacao: NavHostController?, viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts
    val conteudo by viewModel.conteudo

    val options = listOf("Assalto", "Incêndio", "Acidente", "Obra irregular")
    var expanded = remember { mutableStateOf(false) }
    var selectedOption = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nova Ocorrência")

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Título")
        OutlinedTextField(
            value = "",
            onValueChange = {},

        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Categoria")
        Box{
            OutlinedTextField(
                value = selectedOption.value.ifEmpty { "Selecione..." },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{ expanded.value = true},
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            )
        }


        TextField(
            value = conteudo,
            onValueChange = { viewModel.conteudo.value = it },
            placeholder = { Text("O que você está pensando?") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.publicar() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Publicar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Feed", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(posts) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Text(
                        text = post.conteudo,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OccurrenceScreenPreview() {
    MaterialTheme {
        OccurrenceScreen(null)
    }
}
