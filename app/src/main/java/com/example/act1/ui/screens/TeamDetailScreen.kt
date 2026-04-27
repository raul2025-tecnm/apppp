package com.example.act1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.act1.ui.viewmodel.TeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(
    teamId: Int,
    navController: NavController,
    teamViewModel: TeamViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val team by teamViewModel.selectedTeam.collectAsState()
    val totalGoles by teamViewModel.totalGoles.collectAsState()
    val players by playerViewModel.players.collectAsState()
    val isLoadingTeam by teamViewModel.isLoading.collectAsState()
    val isLoadingPlayers by playerViewModel.isLoading.collectAsState()

    LaunchedEffect(teamId) {
        teamViewModel.loadTeam(teamId)
        playerViewModel.loadPlayersByTeam(teamId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(team?.nombre ?: "Detalle de Equipo") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.TeamForm.createRoute(teamId)) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { 
                        teamViewModel.deleteTeam(teamId) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoadingTeam) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                team?.let {
                    Text(text = "Ciudad: ${it.ciudad}", style = MaterialTheme.typography.bodyLarge)
                    it.fundacion?.let { fecha ->
                        Text(text = "Fundación: $fecha", style = MaterialTheme.typography.bodyLarge)
                    }
                    Text(
                        text = "Goles Totales: ${totalGoles ?: 0}",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Jugadores", style = MaterialTheme.typography.titleLarge)
                    
                    if (isLoadingPlayers) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(players) { player ->
                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    onClick = { navController.navigate(Screen.PlayerDetail.createRoute(player.id!!)) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(player.nombre)
                                        Text("Dorsal: ${player.dorsal}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
