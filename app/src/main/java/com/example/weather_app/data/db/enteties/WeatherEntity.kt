package com.example.weather_app.data.db.enteties

import androidx.room.*

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    var id: String,

)