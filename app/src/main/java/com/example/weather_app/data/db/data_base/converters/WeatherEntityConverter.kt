package com.example.weather_app.data.db.data_base.converters

import androidx.room.TypeConverter
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class WeatherEntityConverter {
    var gson = Gson()
    @TypeConverter
    fun stringToWeatherEntityList(data: String): List<WeatherEntity> {
        val listType: Type = object : TypeToken<List<WeatherEntity>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun weatherEntityListToString(userEntityList: List<WeatherEntity>): String {
        return gson.toJson(userEntityList)
    }
}