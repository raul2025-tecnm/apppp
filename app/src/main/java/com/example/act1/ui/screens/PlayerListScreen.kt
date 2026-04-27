package com.example.act1.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.act1.data.model.Jugador
import com.example.act1.ui.navigation.Screen
import com.example.act1.ui.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListScreen(
    navController: NavController,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val players by viewModel.players.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPlayers()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Jugadores") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.PlayerForm.createRoute()) }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Jugador")
            }
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(players) { player ->
                    PlayerItem(player = player) {
                        navController.navigate(Screen.PlayerDetail.createRoute(player.id!!))
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerItem(player: Jugador, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = player.nombre, style = MaterialTheme.typography.titleLarge)
                Text(text = player.posicion, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Dorsal: ${player.dorsal}", style = MaterialTheme.typography.bodySmall)
            }
            Text(text = player.nacionalidad, style = MaterialTheme.typography.titleMedium)
        }
    }
}
