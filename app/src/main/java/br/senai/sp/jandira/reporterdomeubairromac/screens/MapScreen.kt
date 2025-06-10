package br.senai.sp.jandira.reporterdomeubairromac.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState


// Constantes para configuração do mapa
private val DEFAULT_LOCATION = LatLng(-23.56847, -46.88371)
private const val DEFAULT_ZOOM = 12f

@Composable
fun MapScreen(navegacao: NavHostController?, postViewModel: PostViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        postViewModel.getOcorrencias()
    }

    val ocorrencias by postViewModel.listaOcorrencias.observeAsState(initial = emptyList())

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        ocorrencias.forEach { post ->
            // AQUI ESTÁ A MUDANÇA: adicionado '?' após post.endereco
            post.endereco?.firstOrNull()?.let { endereco ->
                val lat = endereco.latitude
                val lng = endereco.longitude

                if (lat != null && lng != null) {
                    Marker(
                        state = MarkerState(position = LatLng(lat, lng)),
                        title = post.titulo ?: "Sem título",
                        snippet = post.descricao ?: "Sem descrição"
                    )
                }
            }
        }
    }
}