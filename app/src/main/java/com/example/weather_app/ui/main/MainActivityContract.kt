package com.example.weather_app.ui.main

import com.example.weather_app.base.MvpPresenter
import com.example.weather_app.base.MvpViewUtils
import com.example.weather_app.domain.inputs.GetCityDetailsInput
import com.example.weather_app.models.CityDetails

interface MainActivityContract {

    interface Presenter : MvpPresenter<View> {
        fun getLocalCityDetails(latitude: Double, longitude: Double)
        fun saveCityDetails(cityDetails: CityDetails)

        fun getCityData(getCityDetailsInput: GetCityDetailsInput)
    }

    interface View: MvpViewUtils {
        fun onGetCityDataSuccess(cityDetails: CityDetails)
        fun onGetCityDataFailed(message: String)
    }
}