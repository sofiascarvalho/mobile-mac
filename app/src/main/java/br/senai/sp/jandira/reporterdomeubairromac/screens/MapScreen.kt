package br.senai.sp.jandira.reporterdomeubairromac.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import br.senai.sp.jandira.reporterdomeubairromac.viewmodel.PostViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.senai.sp.jandira.reporterdomeubairromac.model.GetOcorrencia
import coil.compose.AsyncImage


@Composable
fun OpenStreetMapScreen(navegacao: NavHostController?, postViewModel: PostViewModel = viewModel()) {
    val context = LocalContext.current
    var selectedOcorrencia by remember { mutableStateOf<GetOcorrencia?>(null) }

    // Pede os dados das ocorrências
    LaunchedEffect(Unit) {
        postViewModel.getOcorrencias()
    }

    val ocorrencias by postViewModel.listaOcorrencias.observeAsState(initial = emptyList())



    // Cria o MapView e mantém ele em memória
    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(12.0)
            controller.setCenter(GeoPoint(-23.56847, -46.88371))
        }
    }

    // Atualiza os marcadores quando as ocorrências mudarem
    LaunchedEffect(ocorrencias) {
        mapView.overlays.clear()

        ocorrencias.forEach { post ->
            post.endereco?.firstOrNull()?.let { endereco ->
                val lat = endereco.latitude
                val lng = endereco.longitude

                if (lat != null && lng != null) {
                    val marker = Marker(mapView).apply {
                        position = GeoPoint(lat, lng)
                        title = post.titulo ?: "Sem título"
                        snippet = post.descricao ?: "Sem descrição"
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                        setOnMarkerClickListener { _, _ ->
                            selectedOcorrencia = post // <-- abre popup Compose
                            true
                        }
                    }
                    mapView.overlays.add(marker)
                }
            }
        }
        mapView.invalidate()
    }
    Box(modifier = Modifier.fillMaxSize()) {

        // Renderiza o mapa
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        // Popup se houver seleção
        selectedOcorrencia?.let { post ->
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = post.titulo ?: "Sem título",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = post.descricao ?: "Sem descrição",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Imagem (usando Coil)
                    val imagemUrl = post.midia?.firstOrNull()?.url
                        ?: "https://via.placeholder.com/150"

                    AsyncImage(
                        model = imagemUrl,
                        contentDescription = "Imagem da ocorrência",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Fechar",
                            color = Color.Blue,
                            modifier = Modifier
                                .clickable { selectedOcorrencia = null }
                        )
                    }
                }
            }
        }
    }
}
