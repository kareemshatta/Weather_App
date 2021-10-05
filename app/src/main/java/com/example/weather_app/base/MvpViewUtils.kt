package com.example.weather_app.base

interface MvpViewUtils {
    fun onNetworkError()
    fun showLoading()
    fun hideLoading()
    fun showMessage(msg:String)
}