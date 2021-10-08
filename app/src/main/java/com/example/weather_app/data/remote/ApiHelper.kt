package com.example.weather_app.data.remote

import com.example.weather_app.models.CityDataResponse
import com.example.weather_app.models.WeatherDetailsResponse
import io.reactivex.Observable

interface ApiHelper {
    fun getCityData(latitude: Double, longitude: Double): Observable<CityDataResponse?>
    fun getWeatherDetails(latitude: Double, longitude: Double): Observable<WeatherDetailsResponse?>
}