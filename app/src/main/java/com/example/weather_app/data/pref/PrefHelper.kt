package com.example.weather_app.data.pref

interface PrefHelper {
    fun saveToken(token: String)
    fun getToken(): String
}