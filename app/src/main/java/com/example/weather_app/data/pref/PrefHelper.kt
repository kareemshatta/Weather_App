package com.example.weather_app.data.pref

interface PrefHelper {
    fun saveCityDetails(cityDetails: String)
    fun getCityDetails(): String
}