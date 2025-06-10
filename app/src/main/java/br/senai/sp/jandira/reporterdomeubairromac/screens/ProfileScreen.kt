package br.senai.sp.jandira.reporterdomeubairromac.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navegacao: NavHostController?) {
    Column {
        Text(text = "Esta é a tela de Perfil")
    }
}
