package br.senai.sp.jandira.reporterdomeubairromac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.reporterdomeubairromac.screens.*
import br.senai.sp.jandira.reporterdomeubairromac.ui.theme.ReporterDoMeuBairroMacTheme
import com.google.firebase.FirebaseApp // <-- IMPORTANTE

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o Firebase aqui
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            ReporterDoMeuBairroMacTheme {
                val navegacao = rememberNavController()
                NavHost(
                    navController = navegacao,
                    startDestination = "home"
                ) {
                    composable(route = "home") { RegisterScreen(navegacao) }
                    composable(route = "login") { LoginScreen(navegacao) }
                    composable(route = "feed") { HomeScreen(navegacao) }
                    composable(route = "option") { OptionsScreen(navegacao) }
                    composable(route = "occurrence") { OccurrenceScreen(navegacao) }
                    composable(route = "profile") { ProfileScreen(navegacao) }
                }
            }
        }
    }
}
