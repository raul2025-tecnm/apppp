package com.example.act1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.act1.data.model.Equipo
import com.example.act1.ui.viewmodel.TeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamFormScreen(
    teamId: Int?,
    navController: NavController,
    viewModel: TeamViewModel = hiltViewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var fundacion by remember { mutableStateOf("") }
    
    val selectedTeam by viewModel.selectedTeam.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(teamId) {
        if (teamId != null && teamId != -1) {
            viewModel.loadTeam(teamId)
        }
    }

    LaunchedEffect(selectedTeam) {
        selectedTeam?.let {
            if (teamId != null && teamId != -1) {
                nombre = it.nombre
                ciudad = it.ciudad
                fundacion = it.fundacion ?: ""
            }
        }
    }

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text(if (teamId == null || teamId == -1) "Nuevo Equipo" else "Editar Equipo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            ) 
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ciudad,
                onValueChange = { ciudad = it },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = fundacion,
                onValueChange = { fundacion = it },
                label = { Text("Fundación (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = {
                    val team = Equipo(
                        id = if (teamId == -1) null else teamId, 
                        nombre = nombre, 
                        ciudad = ciudad, 
                        fundacion = fundacion.ifBlank { null }
                    )
                    viewModel.saveTeam(team) {
                        Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank() && ciudad.isNotBlank()
            ) {
                Text("Guardar")
            }
        }
    }
}
