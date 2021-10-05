package com.example.weather_app.di

import com.example.weather_app.ui.main.MainActivityContract
import com.example.weather_app.ui.main.MainActivityPresenter
import org.koin.dsl.module

val appModule = module {

    single<MainActivityContract.Presenter> {
        MainActivityPresenter(get())
    }
}