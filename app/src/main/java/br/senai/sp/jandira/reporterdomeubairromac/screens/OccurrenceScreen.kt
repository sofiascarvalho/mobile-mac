package br.senai.sp.jandira.reporterdomeubairromac.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.senai.sp.jandira.reporterdomeubairromac.R
import br.senai.sp.jandira.reporterdomeubairromac.components.CategoriaSelectBox
import br.senai.sp.jandira.reporterdomeubairromac.model.Categoria
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitViaCep
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel
import kotlinx.coroutines.launch

@Composable
fun OccurrenceScreen(navegacao: NavHostController?, viewModel: PostViewModel = viewModel()) {

    var showCepDialog by remember { mutableStateOf(false) }
    val categorias by viewModel.categorias
    val conteudo by viewModel.conteudo

    var categoriaSelecionada by remember { mutableStateOf<Categoria?>(null) }

    var titulo by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var cep by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        selectedImages = uris
        uris.forEach { Log.d("Imagem Selecionada", it.toString()) }
    }

    LaunchedEffect(Unit) {
        viewModel.carregarCategorias()
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Título", color = Color.White)
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
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

            CategoriaSelectBox(viewModel = viewModel) { categoriaSelecionada ->
                // Atualiza o estado da tela com a categoria escolhida
                selectedOption = categoriaSelecionada.nome_categoria
                categoriaSelecionada = categoriaSelecionada
            }


            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Descrição", color = Color.White)
            OutlinedTextField(
                value = conteudo,
                onValueChange = { viewModel.conteudo.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
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
                value = logradouro,
                onValueChange = { logradouro = it },
                label = { Text("Logradouro") },
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
            Row {
                OutlinedTextField(
                    value = bairro,
                    onValueChange = { bairro = it },
                    label = { Text("Bairro") },
                    modifier = Modifier
                        .padding(end = 13.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    label = { Text("Cidade") },
                    modifier = Modifier.weight(1f),
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
                    value = estado,
                    onValueChange = { estado = it },
                    label = { Text("Estado") },
                    modifier = Modifier
                        .padding(end = 13.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                Text(
                    text = "Buscar endereço pelo CEP",
                    color = Color.Cyan,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { showCepDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Adicionar imagens", color = Color.White)
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color(0xffc1121f))
            ) {
                Text("Escolher arquivos")
            }

            selectedImages.forEach { uri ->
                Text(text = uri.lastPathSegment ?: "Imagem selecionada")
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (showCepDialog) {
                AlertDialog(
                    onDismissRequest = { showCepDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            if (cep.length == 8 && cep.all { it.isDigit() }) {
                                coroutineScope.launch {
                                    try {
                                        val endereco = RetrofitViaCep.service.buscarEndereco(cep)
                                        logradouro = endereco.logradouro ?: ""
                                        bairro = endereco.bairro ?: ""
                                        cidade = endereco.localidade ?: ""
                                        estado = endereco.uf ?: ""
                                        showCepDialog = false
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, "CEP inválido", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Text("Buscar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showCepDialog = false }) {
                            Text("Cancelar")
                        }
                    },
                    title = { Text("Digite seu CEP") },
                    text = {
                        OutlinedTextField(
                            value = cep,
                            onValueChange = { cep = it },
                            label = { Text("CEP") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val nomeCategoria = categoriaSelecionada?.nome_categoria ?: run {
                        Toast.makeText(context, "Selecione uma categoria", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModel.publicar(
                        titulo = titulo,
                        categoriaSelecionada = nomeCategoria,
                        imagensUri = selectedImages,
                        context = context,
                        idUsuario = 1,
                        idEndereco = 1,
                        onSuccess = {
                            Toast.makeText(context, "Ocorrência enviada com sucesso!", Toast.LENGTH_SHORT).show()
                            navegacao?.navigate("feed")
                        },
                        onError = { msg ->
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color(0xffc1121f))
            ) {
                Text("Enviar")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
