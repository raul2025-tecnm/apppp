package com.example.act1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.act1.data.model.EstadisticasJugador
import com.example.act1.ui.viewmodel.StatsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsListScreen(
    navController: NavController,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStats()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Estadísticas de Jugadores") }) }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stats) { stat ->
                    StatItem(stat)
                }
            }
        }
    }
}

@Composable
fun StatItem(stat: EstadisticasJugador) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Jugador ID: ${stat.idJugador}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Partido ID: ${stat.idPartido}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Goles: ${stat.goles}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Asistencias: ${stat.asistencias}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Minutos: ${stat.minutosJugados}", style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "TA: ${stat.tarjetasAmarillas}", color = androidx.compose.ui.graphics.Color.Yellow, style = MaterialTheme.typography.bodySmall)
                Text(text = "TR: ${stat.tarjetasRojas}", color = androidx.compose.ui.graphics.Color.Red, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
