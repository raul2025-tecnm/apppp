package com.example.act1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.act1.data.model.Equipo
import com.example.act1.data.model.TotalGolesResponse
import com.example.act1.data.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val repository: FootballRepository
) : ViewModel() {

    private val _teams = MutableStateFlow<List<Equipo>>(emptyList())
    val teams: StateFlow<List<Equipo>> = _teams

    private val _selectedTeam = MutableStateFlow<Equipo?>(null)
    val selectedTeam: StateFlow<Equipo?> = _selectedTeam

    private val _totalGoles = MutableStateFlow<Int?>(null)
    val totalGoles: StateFlow<Int?> = _totalGoles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadTeams() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _teams.value = repository.getEquipos()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadTeam(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _selectedTeam.value = repository.getEquipo(id)
                _totalGoles.value = repository.getTotalGolesEquipo(id).totalGoles
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveTeam(equipo: Equipo, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                if (equipo.id == null) {
                    repository.createEquipo(equipo)
                } else {
                    repository.updateEquipo(equipo.id, equipo)
                }
                loadTeams()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteTeam(id: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteEquipo(id)
                loadTeams()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
