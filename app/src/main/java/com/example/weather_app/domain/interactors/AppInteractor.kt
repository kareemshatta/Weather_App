package com.example.weather_app.domain.interactors

import com.example.weather_app.base.DataResult
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.domain.inputs.GetCityDetailsInput
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.models.CityDetails
import io.reactivex.Observable


interface AppInteractor {
    fun saveCityDetails(cityDetails: CityDetails)
    fun getLocalCityDetails(): CityDetails

    fun getCityData(getCityDetailsInput: GetCityDetailsInput): Observable<DataResult<CityDetails>>
    fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput): Observable<DataResult<WeatherEntity>>

    fun getLocalWeatherDetails(cityDetails: CityDetails): Observable<DataResult<WeatherEntity>>
    fun getAllCities(): Observable<DataResult<List<WeatherEntity?>>>

    fun deleteWeatherDetails(cityId: Int)
}