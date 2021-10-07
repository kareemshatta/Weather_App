package com.example.weather_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.weather_app.R
import com.example.weather_app.base.BaseActivity
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.domain.inputs.GetCityDetailsInput
import com.example.weather_app.models.CityDetails
import com.example.weather_app.ui.home.HomeActivity
import com.example.weather_app.utils.AnimationUtils.startSplashAnimation
import com.example.weather_app.utils.LocationException
import com.example.weather_app.utils.LocationFetcher
import com.example.weather_app.utils.LocationResponseCallback
import com.example.weather_app.utils.LocationResultModel
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainActivityContract.Presenter>(),
    MainActivityContract.View {
    var mainActivityBinding: ActivityMainBinding? = null
    override val presenter: MainActivityContract.Presenter by inject()

    private lateinit var locationFetcher: LocationFetcher


    override fun getLayoutResource() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        mainActivityBinding = ActivityMainBinding.bind(view)
        presenter.attachView(this)

        mainActivityBinding?.startSplashAnimation(compositeDisposable)
        mainActivityBinding?.continueButton?.setOnClickListener {
            handleLocationRequest()
        }
    }

    private fun handleLocationRequest() {
        locationFetcher = LocationFetcher(this)
        locationFetcher.getLastLocation(object : LocationResponseCallback {
            override fun onSuccess(locationResultModel: LocationResultModel) {
                val cityDetails = CityDetails(
                    latitude = locationResultModel.lat,
                    longitude = locationResultModel.long
                )
                presenter.getCityData(
                    GetCityDetailsInput(
                        lat = cityDetails.latitude,
                        lon = cityDetails.longitude
                    )
                )
            }

            override fun onFailed(locationException: LocationException) {
                presenter.saveCityDetails(CityDetails())
                startActivity(intentFor<HomeActivity>())
                finish()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        locationFetcher.onActivityResult(
            requestCode,
            resultCode,
            data
        )
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun fullScreen() = true

    override fun onGetCityDataSuccess(cityDetails: CityDetails) {
        presenter.saveCityDetails(cityDetails)
        startActivity(intentFor<HomeActivity>())
        finish()
    }

    override fun onGetCityDataFailed(message: String) {
        presenter.saveCityDetails(CityDetails())
        showMessage(message)
        startActivity(intentFor<HomeActivity>())
        finish()

    }
}