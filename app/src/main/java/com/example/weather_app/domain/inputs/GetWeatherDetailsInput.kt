package com.example.weather_app.domain.inputs

import com.example.weather_app.models.CityDetails

data class GetWeatherDetailsInput(
    val cityDetails: CityDetails
)
