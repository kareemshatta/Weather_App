package com.example.weather_app.models

import com.example.weather_app.data.db.enteties.CurrentWeather
import com.example.weather_app.data.db.enteties.DailyWeather

data class WeatherDetailsResponse(
    val current: CurrentWeather,
    val daily: List<DailyWeather>,
    val lat: Double,
    val lon: Double
)