package com.example.weather_app.data.db

import com.example.weather_app.data.db.data_base.RepoDao
import com.example.weather_app.data.db.enteties.WeatherEntity
import io.reactivex.Observable

class DbApiHelperImpl(private val repoDao: RepoDao) : DbApiHelper {
    override fun getWeatherDetailsList(): Observable<List<WeatherEntity?>?> {
        return repoDao.getWeatherDetailsList()
    }

    override fun getWeatherDetails(cityName: String): Observable<List<WeatherEntity?>?> {
        return repoDao.getWeatherDetails(cityName)
    }

    override fun insertWeatherDetails(weatherEntity: WeatherEntity): Long? {
        return repoDao.insertWeatherDetails(weatherEntity)
    }

    override fun deleteWeatherDetails(cityId: Int): Int {
        return repoDao.deleteWeatherDetails(cityId.toString())
    }

}