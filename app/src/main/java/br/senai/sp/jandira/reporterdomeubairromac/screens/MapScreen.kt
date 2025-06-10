package br.senai.sp.jandira.reporterdomeubairromac.screens

import androidx.compose.runtime.Composable
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel

@Composable
fun MapaScreen(postViewModel: PostViewModel) {
    val context = LocalContext.current
    val ocorrencias by postViewModel.ocorrencias

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-23.5505, -46.6333), 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        ocorrencias.forEach { post ->
            val lat = post.endereco[0].latitude.toDouble()
            val lng = post.endereco[0].longitude.toDouble()

            Marker(
                position = LatLng(lat, lng),
                title = post.titulo,
                snippet = post.descricao
            )
        }
    }
}

class LocalContext {

}
