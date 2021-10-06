package com.example.weather_app.data.pref

import android.content.Context
import com.example.weather_app.models.UserLocation
import com.google.gson.Gson

class PrefHelperImpl(private val context: Context) : PrefHelper {
    companion object {
        const val PREF_USER_LOCATION_KEY = "PREF_USER_LOCATION_KEY"
        const val SHARED_KEY = "SHARED_KEY"
    }

    private var preferencesEditor = context.getSharedPreferences(
        SHARED_KEY,
        Context.MODE_PRIVATE
    )

    override fun saveUserLocation(userLocation: String) {
        preferencesEditor.edit().putString(PREF_USER_LOCATION_KEY, userLocation).apply()
    }

    override fun getUserLocation(): String {
        val defaultLocation = Gson().toJson(UserLocation())
        return preferencesEditor.getString(PREF_USER_LOCATION_KEY, defaultLocation) ?: defaultLocation
    }
}