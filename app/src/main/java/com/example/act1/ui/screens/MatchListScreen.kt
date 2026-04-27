package com.example.act1.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.example.act1.data.model.Partido
import com.example.act1.ui.navigation.Screen
import com.example.act1.ui.viewmodel.MatchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchListScreen(
    navController: NavController,
    viewModel: MatchViewModel = hiltViewModel()
) {
    val matches by viewModel.matches.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMatches()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Partidos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.MatchForm.createRoute()) }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Partido")
            }
        }
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
                items(matches) { match ->
                    MatchItem(
                        match = match,
                        onEdit = { navController.navigate(Screen.MatchForm.createRoute(match.id)) },
                        onDelete = { viewModel.deleteMatch(match.id!!) { viewModel.loadMatches() } }
                    )
                }
            }
        }
    }
}

@Composable
fun MatchItem(match: Partido, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onEdit() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Local ID: ${match.equipoLocal} vs Visita ID: ${match.equipoVisita}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Resultado: ${match.golesLocal} - ${match.golesVisita}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Estadio: ${match.estadio}", style = MaterialTheme.typography.bodySmall)
                match.fecha?.let { Text(text = "Fecha: $it", style = MaterialTheme.typography.bodySmall) }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
