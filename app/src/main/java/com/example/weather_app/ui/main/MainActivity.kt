package com.example.weather_app.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.weather_app.R
import com.example.weather_app.base.BaseActivity
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.models.UserLocation
import com.example.weather_app.ui.home.HomeActivity
import com.example.weather_app.utils.AnimationUtils.startSplashAnimation
import com.example.weather_app.utils.Permission
import com.google.android.gms.location.*
import org.koin.android.ext.android.inject
import org.jetbrains.anko.intentFor

class MainActivity : BaseActivity<MainActivityContract.Presenter>(),
    MainActivityContract.View {
    var mainActivityBinding: ActivityMainBinding? = null
    override val presenter: MainActivityContract.Presenter by inject()

    private lateinit var mFusedLocationClient: FusedLocationProviderClient


    override fun getLayoutResource() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        mainActivityBinding = ActivityMainBinding.bind(view)
        presenter.attachView(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mainActivityBinding?.startSplashAnimation(compositeDisposable)
        mainActivityBinding?.continueButton?.setOnClickListener {
            checkLocationPermissions()
        }
    }

    private fun checkLocationPermissions() {
        if (Permission.hasLocationPermissions(this)) {
            getLastLocation()
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {
        Permission.checkLocationPermission(this, object : Permission.Callback {
            override fun isPermissionAccepted(isAccepted: Boolean) {
                if (isAccepted) {
                    getLastLocation()
                } else {
                    showMessage("Permission not granted, you can change this from the settings.")
                    startActivity(intentFor<HomeActivity>())
                    finish()
                }
            }
        })
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationClient.lastLocation.addOnSuccessListener {
                presenter.saveUserLocation(
                    UserLocation(
                        latitude = it.latitude.toFloat(),
                        longitude = it.longitude.toFloat()
                    )
                )
            }
        }
        startActivity(intentFor<HomeActivity>())
        finish()
    }

    override fun fullScreen() = true
}