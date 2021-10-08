package com.example.weather_app.ui.home

import com.example.weather_app.base.MvpPresenter
import com.example.weather_app.base.MvpViewUtils
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.models.CityDetails

interface HomeActivityContract {

    interface Presenter : MvpPresenter<View> {
        fun getLocalCityDetails(): CityDetails
        fun getLocalWeatherDetails(cityDetails: CityDetails)
        fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput)
        fun getAllCities()
        fun deleteWeatherDetails(cityId: Int)
    }

    interface View: MvpViewUtils {
        fun onGetWeatherDetailsSuccess(weatherEntity: WeatherEntity)
        fun onGetLocalWeatherDetailsFailed(cityDetails: CityDetails)
        fun onGetRemoteWeatherDetailsFailed(message: String)
        fun onGetAllCitiesSuccess(cities: List<WeatherEntity?>)
        fun onGetAllCitiesFailed(message: String)
    }


}