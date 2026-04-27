package com.example.act1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.act1.data.model.Entrenador
import com.example.act1.ui.viewmodel.CoachViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoachFormScreen(
    coachId: Int?,
    navController: NavController,
    viewModel: CoachViewModel = hiltViewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var idEquipo by remember { mutableStateOf("") }
    
    val coaches by viewModel.coaches.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCoaches()
    }

    LaunchedEffect(coaches) {
        if (coachId != null) {
            coaches.find { it.id == coachId }?.let {
                nombre = it.nombre
                especialidad = it.especialidad
                idEquipo = it.idEquipo.toString()
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(if (coachId == null) "Nuevo Entrenador" else "Editar Entrenador") }) }
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
                value = especialidad,
                onValueChange = { especialidad = it },
                label = { Text("Especialidad") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = idEquipo,
                onValueChange = { idEquipo = it },
                label = { Text("ID de Equipo") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            Button(
                onClick = {
                    val coach = Entrenador(
                        id = coachId,
                        nombre = nombre,
                        especialidad = especialidad,
                        idEquipo = idEquipo.toIntOrNull() ?: 0
                    )
                    viewModel.saveCoach(coach) {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank() && especialidad.isNotBlank() && idEquipo.isNotBlank()
            ) {
                Text("Guardar")
            }
        }
    }
}
