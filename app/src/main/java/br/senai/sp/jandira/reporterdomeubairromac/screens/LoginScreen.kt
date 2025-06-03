package br.senai.sp.jandira.reporterdomeubairromac.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.senai.sp.jandira.reporterdomeubairromac.R
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navegacao: NavHostController?) {

    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel()

    val emailState = remember { mutableStateOf("") }
    val senhaState = remember { mutableStateOf("") }
    val erroState = remember { mutableStateOf("") }

    // Criar canal de notificação (necessário no Android 8+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "LOGIN_CHANNEL_ID",
                "Canal de Login",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificações relacionadas ao login"
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.wallpaper_city),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xbb000000))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.logo_nova),
                contentDescription = null,
                tint = Color(0xcffc1121),
                modifier = Modifier.size(75.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("LOGIN", color = Color.White, fontSize = 52.sp)

                Spacer(modifier = Modifier.height(130.dp))

                CustomTextFieldLogin(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = "Email"
                )

                CustomTextFieldLogin(
                    value = senhaState.value,
                    onValueChange = { senhaState.value = it },
                    label = "Senha",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        loginViewModel.login(
                            email = emailState.value,
                            senha = senhaState.value,
                            onSuccess = { usuario ->
                                erroState.value = ""
                                showLoginNotification(context)
                                navegacao?.navigate("feed")
                            },
                            onError = { msg ->
                                erroState.value = msg
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xffc1121f)),
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .width(150.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Login", color = Color.White, fontSize = 18.sp)
                }

                if (erroState.value.isNotEmpty()) {
                    Text(
                        text = erroState.value,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Text("Don't have an account?", color = Color.White,
                    modifier = Modifier.clickable {
                        navegacao?.navigate("home")
                    })

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Stay disconnected",
                    color = Color(0xffc1121f),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

// Notificação de login
fun showLoginNotification(context: Context) {
    val builder = NotificationCompat.Builder(context, "LOGIN_CHANNEL_ID")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Login realizado")
        .setContentText("Bem-vindo de volta!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    NotificationManagerCompat.from(context).notify(1001, builder.build())
}

// TextField reutilizável
@Composable
fun CustomTextFieldLogin(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color.White) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .height(60.dp)
            .width(322.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLogin() {
    LoginScreen(null)
}
