package com.example.act1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.act1.data.model.Partido
import com.example.act1.data.model.PartidoResultado
import com.example.act1.data.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val repository: FootballRepository
) : ViewModel() {

    private val _matches = MutableStateFlow<List<Partido>>(emptyList())
    val matches: StateFlow<List<Partido>> = _matches

    private val _matchResults = MutableStateFlow<List<PartidoResultado>>(emptyList())
    val matchResults: StateFlow<List<PartidoResultado>> = _matchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadMatches() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _matches.value = repository.getPartidos()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMatchResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _matchResults.value = repository.getPartidosResultados()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveMatch(partido: Partido, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                if (partido.id == null) {
                    repository.createPartido(partido)
                } else {
                    repository.updatePartido(partido.id, partido)
                }
                loadMatches()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteMatch(id: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deletePartido(id)
                loadMatches()
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
