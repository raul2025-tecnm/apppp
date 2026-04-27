package com.example.act1.data.remote

import com.example.act1.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface FootballApi {
    // Equipos
    @GET("equipos")
    suspend fun getEquipos(): List<Equipo>

    @GET("equipos/{id}")
    suspend fun getEquipo(@Path("id") id: Int): Equipo

    @POST("equipos")
    suspend fun createEquipo(@Body equipo: Equipo): Equipo

    @PUT("equipos/{id}")
    suspend fun updateEquipo(@Path("id") id: Int, @Body equipo: Equipo): Equipo

    @DELETE("equipos/{id}")
    suspend fun deleteEquipo(@Path("id") id: Int): Response<Unit>

    // Jugadores
    @GET("jugadores")
    suspend fun getJugadores(): List<Jugador>

    @GET("jugadores/{id}")
    suspend fun getJugador(@Path("id") id: Int): Jugador

    @POST("jugadores")
    suspend fun createJugador(@Body jugador: Jugador): Jugador

    @PUT("jugadores/{id}")
    suspend fun updateJugador(@Path("id") id: Int, @Body jugador: Jugador): Jugador

    @DELETE("jugadores/{id}")
    suspend fun deleteJugador(@Path("id") id: Int): Response<Unit>

    // Entrenadores
    @GET("entrenadores")
    suspend fun getEntrenadores(): List<Entrenador>

    @POST("entrenadores")
    suspend fun createEntrenador(@Body entrenador: Entrenador): Entrenador

    @PUT("entrenadores/{id}")
    suspend fun updateEntrenador(@Path("id") id: Int, @Body entrenador: Entrenador): Entrenador

    @DELETE("entrenadores/{id}")
    suspend fun deleteEntrenador(@Path("id") id: Int): Response<Unit>

    // Partidos
    @GET("partidos")
    suspend fun getPartidos(): List<Partido>

    @POST("partidos")
    suspend fun createPartido(@Body partido: Partido): Partido

    @PUT("partidos/{id}")
    suspend fun updatePartido(@Path("id") id: Int, @Body partido: Partido): Partido

    @DELETE("partidos/{id}")
    suspend fun deletePartido(@Path("id") id: Int): Response<Unit>

    // Estadisticas Jugador
    @GET("estadisticas")
    suspend fun getEstadisticas(): List<EstadisticasJugador>

    @POST("estadisticas")
    suspend fun createEstadistica(@Body estadistica: EstadisticasJugador): EstadisticasJugador

    @PUT("estadisticas/{id}")
    suspend fun updateEstadistica(@Path("id") id: Int, @Body estadistica: EstadisticasJugador): EstadisticasJugador

    @DELETE("estadisticas/{id}")
    suspend fun deleteEstadistica(@Path("id") id: Int): Response<Unit>

    // Consultas Especiales
    @GET("jugadores/equipo/{idEquipo}")
    suspend fun getJugadoresByEquipo(@Path("idEquipo") idEquipo: Int): List<Jugador>

    @GET("jugadores/goles-mayores-a/{totalGoles}")
    suspend fun getGoleadores(@Path("totalGoles") totalGoles: Int): List<Jugador>

    @GET("equipos/{idEquipo}/total-goles")
    suspend fun getTotalGolesEquipo(@Path("idEquipo") idEquipo: Int): TotalGolesResponse

    @GET("partidos/resultados")
    suspend fun getPartidosResultados(): List<PartidoResultado>
}
