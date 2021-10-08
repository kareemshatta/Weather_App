package com.example.weather_app.data.db.data_base

import androidx.room.*
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.models.CityDetails
import io.reactivex.Observable

@Dao
interface RepoDao {
    @Query("SELECT * FROM weather_table")
    fun getWeatherDetailsList(): Observable<List<WeatherEntity?>?>

    @Query("SELECT * FROM weather_table where place_name = :cityName")
    fun getWeatherDetails(cityName: String): Observable<List<WeatherEntity?>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherDetails(weatherEntity: WeatherEntity): Long?

    @Query("Delete FROM weather_table where placeId = :cityId")
    fun deleteWeatherDetails(cityId: String): Int
}