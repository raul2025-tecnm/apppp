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
import com.example.act1.data.model.Equipo
import com.example.act1.ui.navigation.Screen
import com.example.act1.ui.viewmodel.TeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamListScreen(
    navController: NavController,
    viewModel: TeamViewModel = hiltViewModel()
) {
    val teams by viewModel.teams.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTeams()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Equipos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.TeamForm.createRoute()) }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Equipo")
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
                items(teams) { team ->
                    TeamItem(team = team) {
                        navController.navigate(Screen.TeamDetail.createRoute(team.id!!))
                    }
                }
            }
        }
    }
}

@Composable
fun TeamItem(team: Equipo, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = team.nombre, style = MaterialTheme.typography.titleLarge)
            Text(text = team.ciudad, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
