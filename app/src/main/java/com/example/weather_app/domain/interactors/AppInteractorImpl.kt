package com.example.weather_app.domain.interactors

import com.example.weather_app.data.db.DbApiHelper
import com.example.weather_app.data.pref.PrefHelper
import com.example.weather_app.data.remote.ApiHelper
import com.example.weather_app.data.resource_helper.ResourceHelper
import com.example.weather_app.models.UserLocation
import com.google.gson.Gson

class AppInteractorImpl(
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    private val dbApiHelper: DbApiHelper,
    private val resourceHelper: ResourceHelper
) : AppInteractor {
    override fun saveUserLocation(userLocation: UserLocation) {
        prefHelper.saveUserLocation(Gson().toJson(userLocation))
    }

    override fun getUserLocation(): UserLocation {
        return Gson().fromJson(prefHelper.getUserLocation(), UserLocation::class.java)
    }

}