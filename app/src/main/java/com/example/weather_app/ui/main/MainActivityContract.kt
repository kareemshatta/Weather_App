package com.example.weather_app.ui.main

import com.example.weather_app.base.MvpPresenter
import com.example.weather_app.base.MvpViewUtils
import com.example.weather_app.models.UserLocation

interface MainActivityContract {

    interface Presenter : MvpPresenter<View> {
        fun saveUserLocation(userLocation: UserLocation)
    }

    interface View: MvpViewUtils {
    }


}