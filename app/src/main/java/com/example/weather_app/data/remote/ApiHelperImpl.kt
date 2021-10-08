package com.example.weather_app.data.remote

import com.example.weather_app.models.CityDataResponse
import com.example.weather_app.models.WeatherDetailsResponse
import io.reactivex.Observable


class ApiHelperImpl(private val retrofitApiService: RetrofitApiService) : ApiHelper {

    override fun getCityData(latitude: Double, longitude: Double): Observable<CityDataResponse?> {
        return retrofitApiService.getCityData(
            lat = latitude,
            lon = longitude
        )
    }

    override fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    ): Observable<WeatherDetailsResponse?> {
        return retrofitApiService.getWeatherDetails(
            lat = latitude,
            lon = longitude
        )
    }


}