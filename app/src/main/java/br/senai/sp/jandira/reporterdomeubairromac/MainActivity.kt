package br.senai.sp.jandira.reporterdomeubairromac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.reporterdomeubairromac.screens.HomeScreen
import br.senai.sp.jandira.reporterdomeubairromac.screens.LoginScreen
import br.senai.sp.jandira.reporterdomeubairromac.screens.OccurrenceScreen
import br.senai.sp.jandira.reporterdomeubairromac.screens.OptionsScreen
import br.senai.sp.jandira.reporterdomeubairromac.screens.ProfileScreen
import br.senai.sp.jandira.reporterdomeubairromac.screens.RegisterScreen
import br.senai.sp.jandira.reporterdomeubairromac.ui.theme.ReporterDoMeuBairroMacTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    composable(route = "profile"){ ProfileScreen(navegacao) }
                    composable(route = "options"){ OptionsScreen(navegacao) }
                    composable(route = "occurrence"){ OccurrenceScreen(navegacao) }

                }
            }
        }
    }
}