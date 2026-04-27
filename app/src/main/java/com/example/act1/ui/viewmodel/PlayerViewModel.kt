package com.example.act1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.act1.data.model.Jugador
import com.example.act1.data.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: FootballRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<Jugador>>(emptyList())
    val players: StateFlow<List<Jugador>> = _players

    private val _selectedPlayer = MutableStateFlow<Jugador?>(null)
    val selectedPlayer: StateFlow<Jugador?> = _selectedPlayer

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadPlayers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _players.value = repository.getJugadores()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPlayersByTeam(teamId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _players.value = repository.getJugadoresByEquipo(teamId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadTopScorers(minGoles: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _players.value = repository.getGoleadores(minGoles)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPlayer(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _selectedPlayer.value = repository.getJugador(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun savePlayer(jugador: Jugador, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                if (jugador.id == null) {
                    repository.createJugador(jugador)
                } else {
                    repository.updateJugador(jugador.id, jugador)
                }
                loadPlayers()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deletePlayer(id: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteJugador(id)
                loadPlayers()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
