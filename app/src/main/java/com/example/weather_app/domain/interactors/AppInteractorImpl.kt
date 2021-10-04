package com.example.weather_app.domain.interactors

import com.example.weather_app.data.db.DbApiHelper
import com.example.weather_app.data.pref.PrefHelper
import com.example.weather_app.data.remote.ApiHelper
import com.example.weather_app.data.resource_helper.ResourceHelper

class AppInteractorImpl(
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    private val dbApiHelper: DbApiHelper,
    private val resourceHelper: ResourceHelper
) : AppInteractor {

}