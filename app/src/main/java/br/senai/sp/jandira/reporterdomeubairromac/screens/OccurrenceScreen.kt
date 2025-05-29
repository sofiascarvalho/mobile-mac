package br.senai.sp.jandira.reporterdomeubairromac.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.senai.sp.jandira.reporterdomeubairromac.R
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel

@Composable
fun OccurrenceScreen(navegacao: NavHostController?, viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts
    val conteudo by viewModel.conteudo

    val options = listOf("Assalto", "Incêndio", "Acidente", "Obra irregular")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        selectedImages = uris
        uris.forEach{ Log.d("Imagem Selecionada", it.toString())}
    }

    Box(modifier = Modifier.fillMaxSize()){

        Image(
            painter = painterResource(R.drawable.wallpaper_city),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xbb000000)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Nova Ocorrência", color = Color.White, fontSize = 30.sp)

            Column (
                horizontalAlignment = Alignment.Start
            ){

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Título", color = Color.White)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "Categoria", color = Color.White)
                Box{
                    OutlinedTextField(
                        value = selectedOption.ifEmpty { "Selecione..." },
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {expanded = false}
                    ) {
                        options.forEach{option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedOption = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "Descrição", color = Color.White)
                OutlinedTextField(
                    value = conteudo,
                    onValueChange = { viewModel.conteudo.value = it },
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "Endereço", color = Color.White)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Logradouro") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row{
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Bairro") },
                        modifier = Modifier.padding(end = 13.dp).width(170.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Cidade") },
                        modifier = Modifier.width(170.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Estado") },
                        modifier = Modifier.padding(end = 13.dp).width(170.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "CEP") },
                        modifier = Modifier.width(170.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Adicionar imagens", color = Color.White)
                Button(onClick = {imagePickerLauncher.launch("image/*")},
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xffc1121f))
                ) {
                    Text("Escolher arquivos")
                }

                selectedImages.forEach { uri ->
                    Text(text = uri.lastPathSegment ?: "Imagem selecionada")
                }

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    onClick = { viewModel.publicar() },
                    modifier = Modifier.align(Alignment.End),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xffc1121f))
                ) {
                    Text("Enviar", modifier = Modifier.clickable {
                        navegacao?.navigate("feed")
                    })
                }

                Spacer(modifier = Modifier.height(16.dp))
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
