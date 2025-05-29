package br.senai.sp.jandira.reporterdomeubairromac.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.reporterdomeubairromac.R

@Composable
fun HomeScreen(navegacao: NavHostController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.wallpaper_city),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xbb000000)))

        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Column (
                modifier = Modifier.fillMaxWidth().height(60.dp).background(Color(0x44ffffff)),
                verticalArrangement = Arrangement.Center
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        imageVector = Icons.Default.AddCircle, contentDescription = "Fazer ocorrência",
                        modifier = Modifier.clickable {
                            navegacao?.navigate("occurrence")
                        }
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search, contentDescription = "Pesquisar cidade",
                                tint = Color.Black, modifier = Modifier.padding(3.dp)
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .height(45.dp)
                            .width(259.dp),
                    )
                    Icon(
                        imageVector = Icons.Default.AccountCircle, contentDescription = "Perfil",
                        modifier = Modifier.clickable {
                            navegacao?.navigate("profile")
                        }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(null)
}