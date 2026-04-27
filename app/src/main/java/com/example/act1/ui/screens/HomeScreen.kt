package com.example.act1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.act1.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Fútbol") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a la App de Gestión de Fútbol",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            Button(
                onClick = { navController.navigate(Screen.TeamList.route) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Gestionar Equipos")
            }
            
            Button(
                onClick = { navController.navigate(Screen.PlayerList.route) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Gestionar Jugadores")
            }
            
            Button(
                onClick = { navController.navigate(Screen.MatchResults.route) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Ver Resultados de Partidos")
            }

            OutlinedButton(
                onClick = { navController.navigate(Screen.Goleadores.route) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Buscador de Goleadores")
            }
        }
    }
}
