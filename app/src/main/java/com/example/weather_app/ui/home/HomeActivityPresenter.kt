package com.example.weather_app.ui.home

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.domain.interactors.AppInteractor

class HomeActivityPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<HomeActivityContract.View>(),
    HomeActivityContract.Presenter {

}