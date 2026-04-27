package com.example.act1.data.repository

import com.example.act1.data.model.*
import com.example.act1.data.remote.FootballApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FootballRepository @Inject constructor(
    private val api: FootballApi
) {
    // Equipos
    suspend fun getEquipos() = api.getEquipos()
    suspend fun getEquipo(id: Int) = api.getEquipo(id)
    suspend fun createEquipo(equipo: Equipo) = api.createEquipo(equipo)
    suspend fun updateEquipo(id: Int, equipo: Equipo) = api.updateEquipo(id, equipo)
    suspend fun deleteEquipo(id: Int) = api.deleteEquipo(id)

    // Jugadores
    suspend fun getJugadores() = api.getJugadores()
    suspend fun getJugador(id: Int) = api.getJugador(id)
    suspend fun createJugador(jugador: Jugador) = api.createJugador(jugador)
    suspend fun updateJugador(id: Int, jugador: Jugador) = api.updateJugador(id, jugador)
    suspend fun deleteJugador(id: Int) = api.deleteJugador(id)

    // Entrenadores
    suspend fun getEntrenadores() = api.getEntrenadores()
    suspend fun createEntrenador(entrenador: Entrenador) = api.createEntrenador(entrenador)
    suspend fun updateEntrenador(id: Int, entrenador: Entrenador) = api.updateEntrenador(id, entrenador)
    suspend fun deleteEntrenador(id: Int) = api.deleteEntrenador(id)

    // Partidos
    suspend fun getPartidos() = api.getPartidos()
    suspend fun createPartido(partido: Partido) = api.createPartido(partido)
    suspend fun updatePartido(id: Int, partido: Partido) = api.updatePartido(id, partido)
    suspend fun deletePartido(id: Int) = api.deletePartido(id)

    // Estadisticas
    suspend fun getEstadisticas() = api.getEstadisticas()
    suspend fun createEstadistica(estadistica: EstadisticasJugador) = api.createEstadistica(estadistica)
    suspend fun updateEstadistica(id: Int, estadistica: EstadisticasJugador) = api.updateEstadistica(id, estadistica)
    suspend fun deleteEstadistica(id: Int) = api.deleteEstadistica(id)

    // Special queries
    suspend fun getJugadoresByEquipo(idEquipo: Int) = api.getJugadoresByEquipo(idEquipo)
    suspend fun getGoleadores(totalGoles: Int) = api.getGoleadores(totalGoles)
    suspend fun getTotalGolesEquipo(idEquipo: Int) = api.getTotalGolesEquipo(idEquipo)
    suspend fun getPartidosResultados() = api.getPartidosResultados()
}
