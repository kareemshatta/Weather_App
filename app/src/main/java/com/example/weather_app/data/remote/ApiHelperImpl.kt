package com.example.weather_app.data.remote

import com.example.weather_app.models.CityDataResponse
import io.reactivex.Observable


class ApiHelperImpl(private val retrofitApiService: RetrofitApiService) : ApiHelper {

    override fun getCityData(latitude: Double, longitude: Double): Observable<CityDataResponse?> {
        return retrofitApiService.getCityData(
            lat = latitude,
            lon = longitude
        )
    }


}