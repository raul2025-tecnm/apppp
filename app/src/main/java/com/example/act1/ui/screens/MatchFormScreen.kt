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
import com.example.act1.data.model.Partido
import com.example.act1.ui.viewmodel.MatchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchFormScreen(
    matchId: Int?,
    navController: NavController,
    viewModel: MatchViewModel = hiltViewModel()
) {
    var localId by remember { mutableStateOf("") }
    var visitaId by remember { mutableStateOf("") }
    var golesLocal by remember { mutableStateOf("0") }
    var golesVisita by remember { mutableStateOf("0") }
    var fecha by remember { mutableStateOf("") }
    var estadio by remember { mutableStateOf("") }

    val matches by viewModel.matches.collectAsState()
    
    LaunchedEffect(matchId) {
        viewModel.loadMatches()
    }

    LaunchedEffect(matches) {
        if (matchId != null) {
            matches.find { it.id == matchId }?.let {
                localId = it.equipoLocal.toString()
                visitaId = it.equipoVisita.toString()
                golesLocal = it.golesLocal.toString()
                golesVisita = it.golesVisita.toString()
                fecha = it.fecha ?: ""
                estadio = it.estadio
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(if (matchId == null) "Nuevo Partido" else "Editar Partido") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = localId,
                onValueChange = { localId = it },
                label = { Text("ID Equipo Local") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = visitaId,
                onValueChange = { visitaId = it },
                label = { Text("ID Equipo Visitante") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = estadio,
                onValueChange = { estadio = it },
                label = { Text("Estadio") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = golesLocal,
                    onValueChange = { golesLocal = it },
                    label = { Text("Goles Local") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = golesVisita,
                    onValueChange = { golesVisita = it },
                    label = { Text("Goles Visitante") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = {
                    val match = Partido(
                        id = matchId,
                        equipoLocal = localId.toIntOrNull() ?: 0,
                        equipoVisita = visitaId.toIntOrNull() ?: 0,
                        golesLocal = golesLocal.toIntOrNull() ?: 0,
                        golesVisita = golesVisita.toIntOrNull() ?: 0,
                        fecha = fecha.ifBlank { null },
                        estadio = estadio
                    )
                    viewModel.saveMatch(match) {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = localId.isNotBlank() && visitaId.isNotBlank() && estadio.isNotBlank()
            ) {
                Text("Guardar")
            }
        }
    }
}
