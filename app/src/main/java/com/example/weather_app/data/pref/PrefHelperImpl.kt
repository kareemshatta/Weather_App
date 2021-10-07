package com.example.weather_app.data.pref

import android.content.Context
import com.example.weather_app.models.CityDetails
import com.google.gson.Gson

class PrefHelperImpl(private val context: Context) : PrefHelper {
    companion object {
        const val PREF_CITY_DETAILS_KEY = "PREF_CITY_DETAILS_KEY"
        const val SHARED_KEY = "SHARED_KEY"
    }

    private var preferencesEditor = context.getSharedPreferences(
        SHARED_KEY,
        Context.MODE_PRIVATE
    )

    override fun saveCityDetails(cityDetails: String) {
        preferencesEditor.edit().putString(PREF_CITY_DETAILS_KEY, cityDetails).apply()
    }

    override fun getCityDetails(): String {
        val defaultCity = Gson().toJson(CityDetails())
        return preferencesEditor.getString(PREF_CITY_DETAILS_KEY, defaultCity) ?: defaultCity
    }
}