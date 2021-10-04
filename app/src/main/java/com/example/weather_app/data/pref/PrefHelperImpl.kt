package com.example.weather_app.data.pref

import android.content.Context

class PrefHelperImpl(private val context: Context) : PrefHelper {
    companion object {
        const val PREF_TOKEN_KEY = "PREF_TOKEN_KEY"
        const val SHARED_KEY = "SHARED_KEY"
    }

    private var preferencesEditor = context.getSharedPreferences(
        SHARED_KEY,
        Context.MODE_PRIVATE
    )

    override fun saveToken(token: String) {
        preferencesEditor.edit().putString(PREF_TOKEN_KEY, token).apply()
    }

    override fun getToken(): String {
        return preferencesEditor.getString(PREF_TOKEN_KEY, "") ?: ""
    }
}