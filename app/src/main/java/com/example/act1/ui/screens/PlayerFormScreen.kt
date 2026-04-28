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
import com.example.act1.data.model.Jugador
import com.example.act1.ui.viewmodel.PlayerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerFormScreen(
    playerId: Int?,
    navController: NavController,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var posicion by remember { mutableStateOf("") }
    var dorsal by remember { mutableStateOf("") }
    var fechaNac by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("") }
    var idEquipo by remember { mutableStateOf("") }
    
    val selectedPlayer by viewModel.selectedPlayer.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(playerId) {
        if (playerId != null && playerId != -1) {
            viewModel.loadPlayer(playerId)
        }
    }

    LaunchedEffect(selectedPlayer) {
        selectedPlayer?.let {
            if (playerId != null && playerId != -1) {
                nombre = it.nombre
                posicion = it.posicion
                dorsal = it.dorsal.toString()
                fechaNac = it.fechaNac ?: ""
                nacionalidad = it.nacionalidad
                idEquipo = it.idEquipo.toString()
            }
        }
    }

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text(if (playerId == null || playerId == -1) "Nuevo Jugador" else "Editar Jugador") },
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
                value = posicion,
                onValueChange = { posicion = it },
                label = { Text("Posición") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = dorsal,
                onValueChange = { dorsal = it },
                label = { Text("Dorsal") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = nacionalidad,
                onValueChange = { nacionalidad = it },
                label = { Text("Nacionalidad") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = idEquipo,
                onValueChange = { idEquipo = it },
                label = { Text("ID de Equipo") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = fechaNac,
                onValueChange = { fechaNac = it },
                label = { Text("Fecha Nacimiento (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = {
                    val player = Jugador(
                        id = if (playerId == -1) null else playerId,
                        nombre = nombre,
                        posicion = posicion,
                        dorsal = dorsal.toIntOrNull() ?: 0,
                        fechaNac = fechaNac.ifBlank { null },
                        nacionalidad = nacionalidad,
                        idEquipo = idEquipo.toIntOrNull() ?: 0
                    )
                    viewModel.savePlayer(player) {
                        Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank() && posicion.isNotBlank() && idEquipo.isNotBlank()
            ) {
                Text("Guardar")
            }
        }
    }
}
