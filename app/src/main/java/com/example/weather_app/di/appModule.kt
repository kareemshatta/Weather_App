package com.example.weather_app.di

import com.example.weather_app.ui.home.HomeActivityContract
import com.example.weather_app.ui.home.HomeActivityPresenter
import com.example.weather_app.ui.main.MainActivityContract
import com.example.weather_app.ui.main.MainActivityPresenter
import org.koin.dsl.module

val appModule = module {

    single<MainActivityContract.Presenter> {
        MainActivityPresenter(get())
    }
    single<HomeActivityContract.Presenter> {
        HomeActivityPresenter(get())
    }
}