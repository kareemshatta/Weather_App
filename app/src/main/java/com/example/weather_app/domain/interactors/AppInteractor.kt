package com.example.weather_app.domain.interactors

import com.example.weather_app.models.UserLocation


interface AppInteractor {
    fun saveUserLocation(userLocation: UserLocation)
    fun getUserLocation(): UserLocation
}