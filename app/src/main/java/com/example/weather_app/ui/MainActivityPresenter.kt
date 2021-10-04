package com.example.weather_app.ui

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.domain.interactors.AppInteractor

class MainActivityPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

}