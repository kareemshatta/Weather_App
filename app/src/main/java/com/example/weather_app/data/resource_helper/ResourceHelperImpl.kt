package com.example.weather_app.data.resource_helper

import android.content.Context

class ResourceHelperImpl(private val context: Context) :ResourceHelper{
    override fun getStringResource(strResId: Int):String {
        return context.getString(strResId)
    }
}