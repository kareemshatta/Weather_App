package com.example.weather_app.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.weather_app.R
import com.example.weather_app.base.BaseActivity
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.models.UserLocation
import com.example.weather_app.ui.home.HomeActivity
import com.example.weather_app.utils.*
import com.example.weather_app.utils.AnimationUtils.startSplashAnimation
import com.google.android.gms.location.*
import org.koin.android.ext.android.inject
import org.jetbrains.anko.intentFor

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
                val userLocation = UserLocation(
                    latitude = locationResultModel.lat.toFloat(),
                    longitude = locationResultModel.long.toFloat()
                )
                presenter.saveUserLocation(userLocation)
                startActivity(intentFor<HomeActivity>())
                finish()
            }

            override fun onFailed(locationException: LocationException) {
                presenter.saveUserLocation(UserLocation())
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
}