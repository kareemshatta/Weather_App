package com.example.weather_app.base

interface MvpPresenter<V> {
    fun attachView(view: V)
    fun deAttachView()
}