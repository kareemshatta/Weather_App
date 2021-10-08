package com.example.weather_app.data.db

import com.example.weather_app.data.db.enteties.WeatherEntity
import io.reactivex.Observable


interface DbApiHelper {
    fun getWeatherDetailsList(): Observable<List<WeatherEntity?>?>
    fun getWeatherDetails(cityName: String): Observable<List<WeatherEntity?>?>
    fun insertWeatherDetails(weatherEntity: WeatherEntity): Long?
    fun deleteWeatherDetails(cityId: Int): Int
}