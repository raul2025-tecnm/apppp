package com.example.act1.ui.screens

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
import com.example.act1.data.model.Entrenador
import com.example.act1.ui.navigation.Screen
import com.example.act1.ui.viewmodel.CoachViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoachListScreen(
    navController: NavController,
    viewModel: CoachViewModel = hiltViewModel()
) {
    val coaches by viewModel.coaches.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCoaches()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Entrenadores") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.CoachForm.createRoute()) }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Entrenador")
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
                items(coaches) { coach ->
                    CoachItem(
                        coach = coach,
                        onDelete = { viewModel.deleteCoach(coach.id!!) { viewModel.loadCoaches() } }
                    )
                }
            }
        }
    }
}

@Composable
fun CoachItem(coach: Entrenador, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = coach.nombre, style = MaterialTheme.typography.titleLarge)
                Text(text = "Especialidad: ${coach.especialidad}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Equipo ID: ${coach.idEquipo}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
