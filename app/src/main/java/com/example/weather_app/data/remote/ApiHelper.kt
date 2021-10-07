package com.example.weather_app.data.remote

import com.example.weather_app.models.CityDataResponse
import io.reactivex.Observable

interface ApiHelper {
    fun getCityData(latitude: Double, longitude: Double): Observable<CityDataResponse?>
}