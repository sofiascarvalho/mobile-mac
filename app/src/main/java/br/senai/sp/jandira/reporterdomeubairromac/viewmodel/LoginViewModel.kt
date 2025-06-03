package br.senai.sp.jandira.reporterdomeubairromac.viewmodel


    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import br.senai.sp.jandira.reporterdomeubairromac.model.LoginRequest
    import br.senai.sp.jandira.reporterdomeubairromac.model.User
    import br.senai.sp.jandira.reporterdomeubairromac.services.RetrofitFactory
    import kotlinx.coroutines.launch
    import retrofit2.Response

    class LoginViewModel : ViewModel() {

        fun login(
            email: String,
            senha: String,
            onSuccess: (User) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                try {
                    val response: Response<User> = RetrofitFactory.userService.loginUser(LoginRequest(email, senha))

                    if (response.isSuccessful && response.body() != null) {
                        onSuccess(response.body()!!)
                    } else {
                        onError("Email ou senha incorretos!")
                    }
                } catch (e: Exception) {
                    onError("Erro de conexão: ${e.localizedMessage}")
                }
            }
        }
    }
