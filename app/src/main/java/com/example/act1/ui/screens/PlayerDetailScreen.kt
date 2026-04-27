package com.example.act1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.act1.ui.navigation.Screen
import com.example.act1.ui.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(
    playerId: Int,
    navController: NavController,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val player by viewModel.selectedPlayer.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(playerId) {
        viewModel.loadPlayer(playerId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(player?.nombre ?: "Detalle de Jugador") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.PlayerForm.createRoute(playerId)) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { 
                        viewModel.deletePlayer(playerId) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                player?.let {
                    Text(text = "Posición: ${it.posicion}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Dorsal: ${it.dorsal}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Nacionalidad: ${it.nacionalidad}", style = MaterialTheme.typography.bodyLarge)
                    it.fechaNac?.let { fecha ->
                        Text(text = "Fecha Nacimiento: $fecha", style = MaterialTheme.typography.bodyLarge)
                    }
                    Text(text = "ID Equipo: ${it.idEquipo}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
