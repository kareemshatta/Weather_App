package com.example.weather_app.data.db.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather_app.data.db.enteties.WeatherEntity

@Database(entities = [WeatherEntity::class], version = AppDatabase.VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao

    companion object {
        const val VERSION = 2
    }
}