package com.example.act1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadCoaches()
    }

    LaunchedEffect(coaches) {
        if (coachId != null && coachId != -1) {
            coaches.find { it.id == coachId }?.let {
                nombre = it.nombre
                especialidad = it.especialidad
                idEquipo = it.idEquipo.toString()
            }
        }
    }

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text(if (coachId == null || coachId == -1) "Nuevo Entrenador" else "Editar Entrenador") },
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
                        id = if (coachId == -1) null else coachId,
                        nombre = nombre,
                        especialidad = especialidad,
                        idEquipo = idEquipo.toIntOrNull() ?: 0
                    )
                    viewModel.saveCoach(coach) {
                        Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
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
