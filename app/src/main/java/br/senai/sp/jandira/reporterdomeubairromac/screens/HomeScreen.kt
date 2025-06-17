package br.senai.sp.jandira.reporterdomeubairromac.screens


import android.net.wifi.hotspot2.pps.HomeSp
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun HomeScreen(navegacao: NavHostController?, viewModel: PostViewModel = viewModel()) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.wallpaper_city),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xAE1A1A1A)))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF494949))
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        navegacao?.navigate("option")
                        Log.d("NAV", "Indo para ProfileScreen")

                    }
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pesquisar cidade",
                        tint = Color.Black,
                        modifier = Modifier.padding(3.dp)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .height(45.dp)
                    .width(259.dp),
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        Log.d("NAV", "Ícone clicado")
                        navegacao?.navigate("option")
                    }
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxSize()
        ){
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(characterList.value){
                    OccurrenceCard(
                        nome = it.nome
                    )
                }
            OccurrenceCard()
            OccurrenceCard()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(null)
}





