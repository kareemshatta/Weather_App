package com.example.weather_app.data.pref

interface PrefHelper {
    fun saveUserLocation(userLocation: String)
    fun getUserLocation(): String
}