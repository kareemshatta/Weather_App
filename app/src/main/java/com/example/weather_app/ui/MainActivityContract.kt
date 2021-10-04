package com.example.weather_app.ui

import com.example.weather_app.base.MvpPresenter
import com.example.weather_app.base.MvpViewUtils

interface MainActivityContract {

    interface Presenter : MvpPresenter<View> {
    }

    interface View: MvpViewUtils {
    }


}