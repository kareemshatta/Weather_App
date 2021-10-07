package com.example.weather_app.models

data class CityDetails (
    //default values for London, UK
    var latitude: Double = 51.509865,
    var longitude: Double = -0.118092,
    var cityName: String = "London",
    var country: String = "UK"
)