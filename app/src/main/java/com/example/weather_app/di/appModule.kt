package com.example.weather_app.di

import com.example.weather_app.ui.MainActivityContract
import com.example.weather_app.ui.MainActivityPresenter
import org.koin.dsl.module

val appModule = module {

    single<MainActivityContract.Presenter> {
        MainActivityPresenter(get())
    }
}