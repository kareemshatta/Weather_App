package com.example.weather_app.ui.home

import com.example.weather_app.base.MvpPresenter
import com.example.weather_app.base.MvpViewUtils

interface HomeActivityContract {

    interface Presenter : MvpPresenter<View> {
    }

    interface View: MvpViewUtils {
    }


}