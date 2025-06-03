package br.senai.sp.jandira.reporterdomeubairromac.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaSelectBox(viewModel: PostViewModel, onCategoriaSelecionada: (Categoria) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecione uma categoria") }

    val categorias = viewModel.categorias.value

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Categoria") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (categorias.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Nenhuma categoria disponível") },
                    onClick = {}
                )
            } else {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.nome_categoria) },
                        onClick = {
                            selectedText = categoria.nome_categoria
                            expanded = false
                            onCategoriaSelecionada(categoria)
                        }
                    )
                }
            }
        }
    }
}
