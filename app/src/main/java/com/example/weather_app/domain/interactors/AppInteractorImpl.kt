package com.example.weather_app.domain.interactors

import com.example.weather_app.R
import com.example.weather_app.base.DataResult
import com.example.weather_app.data.db.DbApiHelper
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.data.pref.PrefHelper
import com.example.weather_app.data.remote.ApiHelper
import com.example.weather_app.data.resource_helper.ResourceHelper
import com.example.weather_app.domain.inputs.GetCityDetailsInput
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.domain.mapper.convertToCityDetailsModel
import com.example.weather_app.domain.mapper.convertToWeatherEntityModel
import com.example.weather_app.models.CityDetails
import com.google.gson.Gson
import io.reactivex.Observable

class AppInteractorImpl(
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    private val dbApiHelper: DbApiHelper,
    private val resourceHelper: ResourceHelper
) : AppInteractor {
    override fun saveCityDetails(cityDetails: CityDetails) {
        prefHelper.saveCityDetails(Gson().toJson(cityDetails))
    }

    override fun getLocalCityDetails(): CityDetails {
        return Gson().fromJson(prefHelper.getCityDetails(), CityDetails::class.java)
    }

    override fun getCityData(getCityDetailsInput: GetCityDetailsInput): Observable<DataResult<CityDetails>> {
        return apiHelper.getCityData(getCityDetailsInput.lat, getCityDetailsInput.lon)
            .map {
                if (it.cod == 200) {
                    val cityDetails = it.convertToCityDetailsModel()
                    DataResult.Success(cityDetails)
                } else {
                    DataResult.Failure(resourceHelper.getStringResource(R.string.network_error))
                }
            }
    }

    override fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput): Observable<DataResult<WeatherEntity>> {
        return apiHelper.getWeatherDetails(
            getWeatherDetailsInput.cityDetails.latitude,
            getWeatherDetailsInput.cityDetails.longitude
        ).map {
            val weatherEntity =
                it.convertToWeatherEntityModel(getWeatherDetailsInput.cityDetails)
            val result = dbApiHelper.insertWeatherDetails(weatherEntity)
            result?.let {
                DataResult.Success(weatherEntity)
            }
                ?: DataResult.Failure(resourceHelper.getStringResource(R.string.error_saving_weather_details))
        }
    }

    override fun getLocalWeatherDetails(cityDetails: CityDetails): Observable<DataResult<WeatherEntity>> {
        return dbApiHelper.getWeatherDetails(cityDetails.cityName).map {
            val weatherEntity = it.firstOrNull()
            weatherEntity?.let { result ->
                result.current?.let {
                    DataResult.Success(result)
                }?:DataResult.Failure("")
            }?: DataResult.Failure("")
        }
    }

    override fun getAllCities(): Observable<DataResult<List<WeatherEntity?>>> {
        return dbApiHelper.getWeatherDetailsList().map {
             if (it.isNotEmpty()){
                 DataResult.Success(it)
             }else{
                 DataResult.Failure(resourceHelper.getStringResource(R.string.network_error))
             }
        }
    }

    override fun deleteWeatherDetails(cityId: Int) {
        dbApiHelper.deleteWeatherDetails(cityId)
    }

}