//package br.senai.sp.jandira.reporterdomeubairromac.screens
//
//import android.annotation.SuppressLint
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory
//import org.osmdroid.util.GeoPoint
//import org.osmdroid.views.MapView
//import org.osmdroid.views.overlay.Marker
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//
//
//@Composable
//fun OpenStreetMapScreen(navegacao: NavHostController?, postViewModel: PostViewModel = viewModel()) {
//    val context = LocalContext.current
//
//    // Pede os dados das ocorrências
//    LaunchedEffect(Unit) {
//        postViewModel.getOcorrencias()
//    }
//
//    val ocorrencias by postViewModel.listaOcorrencias.observeAsState(initial = emptyList())
//
//    // Cria o MapView e mantém ele em memória
//    val mapView = remember {
//        MapView(context).apply {
//            setTileSource(TileSourceFactory.MAPNIK)
//            setMultiTouchControls(true)
//            controller.setZoom(12.0)
//            controller.setCenter(GeoPoint(-23.56847, -46.88371))
//        }
//    }
//
//    // Atualiza os marcadores quando as ocorrências mudarem
//    LaunchedEffect(ocorrencias) {
//        Log.d("OpenStreetMap", "Ocorrencias recebidas: ${ocorrencias.size}")
//        mapView.overlays.clear()  // limpa uma vez só antes de adicionar todos os marcadores
//        ocorrencias.forEach { post ->
//            post.endereco?.firstOrNull()?.let { endereco ->
//                Log.d("OpenStreetMap", "Lat: ${endereco.latitude}, Lng: ${endereco.longitude}")
//                val lat = endereco.latitude
//                val lng = endereco.longitude
//
//                if (lat != null && lng != null) {
//                    val marker = Marker(mapView).apply {
//                        position = GeoPoint(lat, lng)  // Usar coordenadas reais da ocorrência
//                        title = post.titulo ?: "Sem título"
//                        snippet = post.descricao ?: "Sem descrição"
//                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                    }
//                    mapView.overlays.add(marker)
//                }
//            }
//        }
//        mapView.invalidate()
//    }
//
//}
