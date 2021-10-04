package com.example.weather_app.di

import com.example.weather_app.domain.interactors.AppInteractor
import com.example.weather_app.domain.interactors.AppInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    single<AppInteractor> {
        AppInteractorImpl(get(),get(),get(),get())
    }
}
