package br.senai.sp.jandira.reporterdomeubairromac.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.reporterdomeubairromac.R
import br.senai.sp.jandira.reporterdomeubairromac.model.UserRequest
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    fun cadastrarUsuario(
        context: Context,
        user: UserRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response: Response<UserRequest> = RetrofitFactory.userService.registerUser(user)

                if (response.isSuccessful) {
                    showRegistrationNotification(context)
                    onSuccess()
                } else {
                    onError("Erro no cadastro: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Erro de conexão: ${e.localizedMessage}")
            }
        }
    }

    private fun showRegistrationNotification(context: Context) {
        val channelId = "cadastro_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Canal de Cadastro",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificação após cadastro"
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo_nova) // Coloque um ícone válido do seu projeto
            .setContentTitle("Cadastro realizado")
            .setContentText("Seja bem-vindo!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(1001, notification)
    }
}
