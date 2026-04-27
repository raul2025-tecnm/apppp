package com.example.act1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.act1.data.model.Entrenador
import com.example.act1.data.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoachViewModel @Inject constructor(
    private val repository: FootballRepository
) : ViewModel() {

    private val _coaches = MutableStateFlow<List<Entrenador>>(emptyList())
    val coaches: StateFlow<List<Entrenador>> = _coaches

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCoaches() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _coaches.value = repository.getEntrenadores()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveCoach(entrenador: Entrenador, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                if (entrenador.id == null) {
                    repository.createEntrenador(entrenador)
                } else {
                    repository.updateEntrenador(entrenador.id, entrenador)
                }
                loadCoaches()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteCoach(id: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteEntrenador(id)
                loadCoaches()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
