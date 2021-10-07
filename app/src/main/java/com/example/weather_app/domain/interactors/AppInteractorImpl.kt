package com.example.weather_app.domain.interactors

import com.example.weather_app.R
import com.example.weather_app.base.DataResult
import com.example.weather_app.data.db.DbApiHelper
import com.example.weather_app.data.pref.PrefHelper
import com.example.weather_app.data.remote.ApiHelper
import com.example.weather_app.data.resource_helper.ResourceHelper
import com.example.weather_app.domain.inputs.GetCityDetailsInput
import com.example.weather_app.domain.mapper.convertToCityDetailsModel
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
                    DataResult.Success<CityDetails>(cityDetails)
                } else {
                    DataResult.Failure<CityDetails>(resourceHelper.getStringResource(R.string.network_error))
                }
            }
    }

}