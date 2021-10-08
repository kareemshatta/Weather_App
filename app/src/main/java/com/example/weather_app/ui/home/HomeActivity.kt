package com.example.weather_app.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.example.weather_app.R
import com.example.weather_app.base.BaseActivity
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.databinding.ActivityHomeBinding
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.models.CityDetails
import com.example.weather_app.ui.home.adapters.DailyWeatherDetailsAdapter
import com.example.weather_app.ui.home.adapters.RecentsAdapter
import com.example.weather_app.utils.loadImage
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity<HomeActivityContract.Presenter>(),
    HomeActivityContract.View, RecentsAdapter.RecentAdapterCallback {
    var homeActivityBinding: ActivityHomeBinding? = null
    override val presenter: HomeActivityContract.Presenter by inject()
    private lateinit var cityDetails: CityDetails
    private var dailyWeatherDetailsAdapter = DailyWeatherDetailsAdapter()
    private var recentsAdapter = RecentsAdapter(this)

    override fun getLayoutResource() = R.layout.activity_home

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        homeActivityBinding = ActivityHomeBinding.bind(view)
        presenter.attachView(this)
        cityDetails = presenter.getLocalCityDetails()
        presenter.getLocalWeatherDetails(cityDetails)

        homeActivityBinding?.appToolBarLayout?.cityNameTextView?.text = cityDetails.cityName

        setupViewController()
    }

    private fun setupViewController() {

    }

    override fun onGetWeatherDetailsSuccess(weatherEntity: WeatherEntity) {
        setupViewData(weatherEntity)
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewData(weatherEntity: WeatherEntity) {
        homeActivityBinding?.todayWeatherLayout
        homeActivityBinding?.todayWeatherLayout?.humidityTextView?.text =
            weatherEntity.current?.currentHumidity.toString() + "%"
        homeActivityBinding?.todayWeatherLayout?.WeatherStatusTextView?.text =
            weatherEntity.current?.currentWeather?.firstOrNull()?.main
        homeActivityBinding?.todayWeatherLayout?.tempMinMaxTextView?.text =
            weatherEntity.current?.currentWeather?.firstOrNull()?.description
        homeActivityBinding?.todayWeatherLayout?.tempDegreeTextView?.text =
            weatherEntity.current?.currentTemp.toString() + "°C"
        homeActivityBinding?.todayWeatherLayout?.windTextView?.text =
            weatherEntity.current?.currentWindSpeed.toString() + "km/hr"
        homeActivityBinding?.todayWeatherLayout?.weatherIconImageView
            .loadImage("http://openweathermap.org/img/wn/${weatherEntity.current?.currentWeather?.firstOrNull()?.icon}.png")
        weatherEntity.daily?.let {
            dailyWeatherDetailsAdapter.swapData(it)
            homeActivityBinding?.daysRecyclerView?.adapter = dailyWeatherDetailsAdapter
        }
        presenter.getAllCities()
    }

    override fun onGetLocalWeatherDetailsFailed(cityDetails: CityDetails) {
        presenter.getWeatherDetails(GetWeatherDetailsInput(cityDetails))
    }

    override fun onGetRemoteWeatherDetailsFailed(message: String) {
        showMessage(message)
        //show empty layout
    }

    override fun onGetAllCitiesSuccess(cities: List<WeatherEntity?>) {
        var newList = cities.filterNotNull()
        recentsAdapter.swapData(newList)
        homeActivityBinding?.recentRecyclerView?.adapter = recentsAdapter

    }

    override fun onGetAllCitiesFailed(message: String) {
        showMessage(message)
    }

    override fun onDeleteRecentItem(cityID: Int) {
        presenter.deleteWeatherDetails(cityID)
    }

}