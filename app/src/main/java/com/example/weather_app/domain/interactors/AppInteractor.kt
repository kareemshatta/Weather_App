package com.example.weather_app.domain.interactors

import com.example.weather_app.base.DataResult
import com.example.weather_app.domain.inputs.GetCityDetailsInput
import com.example.weather_app.models.CityDetails
import io.reactivex.Observable


interface AppInteractor {
    fun saveCityDetails(cityDetails: CityDetails)
    fun getLocalCityDetails(): CityDetails

    fun getCityData(getCityDetailsInput: GetCityDetailsInput): Observable<DataResult<CityDetails>>
}