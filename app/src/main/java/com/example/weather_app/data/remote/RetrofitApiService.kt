package com.example.weather_app.data.remote

import com.example.weather_app.Constants
import com.example.weather_app.models.CityDataResponse
import com.example.weather_app.models.WeatherDetailsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("data/2.5/weather?")
    fun getCityData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = Constants.APP_ID,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en"
    ): Observable<CityDataResponse?>

    @GET("data/2.5/onecall?")
    fun getWeatherDetails(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = Constants.APP_ID,
        @Query("units") units: String = "metric"
//        ,
//        @Query("exclude") exclude: String = "hourly,alerts,minutely",
//        @Query("lang") lang: String = "en"
    ): Observable<WeatherDetailsResponse?>

}