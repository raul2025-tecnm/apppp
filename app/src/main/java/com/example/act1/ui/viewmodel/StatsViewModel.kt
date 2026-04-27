package com.example.act1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.act1.data.model.EstadisticasJugador
import com.example.act1.data.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: FootballRepository
) : ViewModel() {

    private val _stats = MutableStateFlow<List<EstadisticasJugador>>(emptyList())
    val stats: StateFlow<List<EstadisticasJugador>> = _stats

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadStats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _stats.value = repository.getEstadisticas()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveStats(estadistica: EstadisticasJugador, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                if (estadistica.id == null) {
                    repository.createEstadistica(estadistica)
                } else {
                    repository.updateEstadistica(estadistica.id!!, estadistica)
                }
                loadStats()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteStats(id: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteEstadistica(id)
                loadStats()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
