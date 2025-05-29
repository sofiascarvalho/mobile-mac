package br.senai.sp.jandira.reporterdomeubairromac.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.reporterdomeubairromac.R
import br.senai.sp.jandira.reporterdomeubairromac.model.LoginRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.User
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback

@Composable
fun LoginScreen(navegacao: NavHostController?) {

    val emailState = remember {
        mutableStateOf("")
    }

    val senhaState = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(
                R.drawable.wallpaper_city
            ),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xbb000000))
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        ){
            Icon(
                painter = painterResource(
                    R.drawable.logo_nova
                ),
                contentDescription = "",
                tint = Color(0xcffc1121),
                modifier = Modifier.size(75.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "LOGIN",
                    color = Color.White,
                    fontSize = 52.sp)

                Spacer(modifier = Modifier.height(130.dp))

                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text(text = "Email") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .height(60.dp)
                        .width(322.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xffffffff),
                        unfocusedBorderColor = Color(0xffffffff),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = senhaState.value,
                    onValueChange = { senhaState.value = it },
                    label = { Text(text = "Senha") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 28.dp)
                        .height(60.dp)
                        .width(322.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xffffffff),
                        unfocusedBorderColor = Color(0xffffffff),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                    val loginData = LoginRequest(
                        email = emailState.value,
                        senha = senhaState.value
                    )

                    val call = RetrofitFactory.userService.loginUser(loginData)

                    call.enqueue(object : Callback<User> {
                        override fun onResponse(call: retrofit2.Call<User>, response: retrofit2.Response<User>) {
                            if (response.isSuccessful && response.body() != null) {
                                val usuario = response.body()!!
                                Log.i("LOGIN", "Usuário logado: ${usuario.nome}")
                                navegacao?.navigate("feed")
                            } else {
                                Log.e("LOGIN", "Erro ao fazer login! Código: ${response.code()}")
                                Log.e("LOGIN", "Mensagem: ${response.errorBody()?.string()}")
                            }
                        }
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e("LOGIN", "Falha na requisição: ${t.localizedMessage}", t)
                        }

                    })
                },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xffc1121f)
                    ),
                    modifier = Modifier.padding(top = 25.dp).width(150.dp).height(50.dp).clickable { navegacao?.navigate("feed") },
                    shape = RoundedCornerShape(5.dp)
                ){
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Text(text = "Already have an account?", color = Color.White,
                    modifier = Modifier.clickable {
                        navegacao?.navigate("home")
                    })

                Spacer(modifier = Modifier.height(6.dp))


                Text(text = "Stay disconnected", color = Color(0xffc1121f), textDecoration = TextDecoration.Underline)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(null)
}