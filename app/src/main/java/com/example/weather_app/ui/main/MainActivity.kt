package com.example.weather_app.ui.main

import android.os.Bundle
import android.view.View
import com.example.weather_app.R
import com.example.weather_app.base.BaseActivity
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.ui.home.HomeActivity
import com.example.weather_app.utils.AnimationUtils.startSplashAnimation
import com.example.weather_app.utils.Permission
import org.koin.android.ext.android.inject
import org.jetbrains.anko.intentFor

class MainActivity : BaseActivity<MainActivityContract.Presenter>(),
    MainActivityContract.View {
    var mainActivityBinding: ActivityMainBinding? = null
    override val presenter: MainActivityContract.Presenter by inject()
//    private val airLocation by lazy {
//        AirLocation(this, this, true)
//    }

    override fun getLayoutResource() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        mainActivityBinding = ActivityMainBinding.bind(view)
        presenter.attachView(this)

        mainActivityBinding?.startSplashAnimation(compositeDisposable)
        mainActivityBinding?.continueButton?.setOnClickListener {
            checkLocationPermissions()
        }
    }

    private fun checkLocationPermissions() {
        if (Permission.hasLocationPermissions(this)) {
//            airLocation.start()
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {
        Permission.checkLocationPermission(this, object : Permission.Callback {
            override fun isPermissionAccepted(isAccepted: Boolean) {
                if (isAccepted) {
                    //TODO get last location
//                    airLocation.start()
                } else {
                    showMessage(getString(R.string.permission_denied))
                    startActivity(intentFor<HomeActivity>())
                    finish()
                }
            }

        })
    }

    override fun fullScreen() = true
}