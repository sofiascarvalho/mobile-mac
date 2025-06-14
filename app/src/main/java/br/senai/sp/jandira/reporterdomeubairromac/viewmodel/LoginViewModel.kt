package br.senai.sp.jandira.reporterdomeubairromac.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.reporterdomeubairromac.model.LoginRequest
import br.senai.sp.jandira.reporterdomeubairromac.model.User
import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
import kotlinx.coroutines.launch
import retrofit2.Response
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import br.senai.sp.jandira.reporterdomeubairromac.model.LoginResponse

class LoginViewModel : ViewModel() {

    fun login(
        context: Context,
        email: String,
        senha: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response: Response<LoginResponse> = RetrofitFactory.userService.loginUser(LoginRequest(email, senha))

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    if (loginResponse.users.isNotEmpty()) {
                        val usuario = loginResponse.users[0] // Pega o primeiro usuário da lista

                        // Salva ID no SharedPreferences
                        val sharedPreferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE)
                        sharedPreferences.edit {
                            putInt("id_usuario", usuario.idUsuario)
                            Log.d("LOGIN", "Usuário logado com ID: ${usuario.idUsuario}")
                        }

                        onSuccess(usuario)
                    } else {
                        onError("Usuário não encontrado na resposta.")
                    }
                } else {
                    onError("Email ou senha incorretos!")
                }

            } catch (e: Exception) {
                onError("Erro de conexão: ${e.localizedMessage}")
            }
        }
    }
}
