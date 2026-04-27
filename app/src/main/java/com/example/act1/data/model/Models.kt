package com.example.act1.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Equipo(
    @Json(name = "id_equipo") val id: Int? = null,
    @Json(name = "nombre") val nombre: String,
    @Json(name = "ciudad") val ciudad: String,
    @Json(name = "fundacion") val fundacion: String? = null
)

@JsonClass(generateAdapter = true)
data class Jugador(
    @Json(name = "id_jugador") val id: Int? = null,
    @Json(name = "nombre") val nombre: String,
    @Json(name = "posicion") val posicion: String,
    @Json(name = "dorsal") val dorsal: Int,
    @Json(name = "fecha_nac") val fechaNac: String? = null,
    @Json(name = "nacionalidad") val nacionalidad: String,
    @Json(name = "id_equipo") val idEquipo: Int
)

@JsonClass(generateAdapter = true)
data class Entrenador(
    @Json(name = "id_entrenador") val id: Int? = null,
    @Json(name = "nombre") val nombre: String,
    @Json(name = "especialidad") val especialidad: String,
    @Json(name = "id_equipo") val idEquipo: Int
)

@JsonClass(generateAdapter = true)
data class Partido(
    @Json(name = "id_partido") val id: Int? = null,
    @Json(name = "fecha") val fecha: String? = null,
    @Json(name = "estadio") val estadio: String,
    @Json(name = "equipo_local") val equipoLocal: Int,
    @Json(name = "equipo_visita") val equipoVisita: Int,
    @Json(name = "goles_local") val golesLocal: Int = 0,
    @Json(name = "goles_visita") val golesVisita: Int = 0
)

@JsonClass(generateAdapter = true)
data class EstadisticasJugador(
    @Json(name = "id_estadistica") val id: Int? = null,
    @Json(name = "id_jugador") val idJugador: Int,
    @Json(name = "id_partido") val idPartido: Int,
    @Json(name = "minutos_jugados") val minutosJugados: Int = 0,
    @Json(name = "goles") val goles: Int = 0,
    @Json(name = "asistencias") val asistencias: Int = 0,
    @Json(name = "tarjetas_amarillas") val tarjetasAmarillas: Int = 0,
    @Json(name = "tarjetas_rojas") val tarjetasRojas: Int = 0
)

@JsonClass(generateAdapter = true)
data class PartidoResultado(
    @Json(name = "id_partido") val id: Int,
    @Json(name = "equipo_local_nombre") val equipoLocal: String,
    @Json(name = "equipo_visitante_nombre") val equipoVisitante: String,
    @Json(name = "goles_local") val golesLocal: Int,
    @Json(name = "goles_visita") val golesVisita: Int
)

@JsonClass(generateAdapter = true)
data class TotalGolesResponse(
    @Json(name = "total_goles") val totalGoles: Int
)
