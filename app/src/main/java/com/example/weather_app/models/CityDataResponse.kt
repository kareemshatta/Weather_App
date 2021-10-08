package com.example.weather_app.models

data class CityDataResponse(
    val cod: Int,
    val id: Int,
    val coord: Coord,
    val name: String,
    val sys: Sys
) {
    data class Coord(
        val lat: Double,
        val lon: Double
    )

    data class Sys(
        val country: String
    )
}