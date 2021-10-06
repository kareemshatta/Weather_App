package com.example.weather_app.ui.main

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.domain.interactors.AppInteractor
import com.example.weather_app.models.UserLocation

class MainActivityPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {
    override fun saveUserLocation(userLocation: UserLocation) {
        appInteractor.saveUserLocation(userLocation)
    }
}