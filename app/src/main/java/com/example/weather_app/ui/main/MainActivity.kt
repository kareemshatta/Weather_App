package com.example.weather_app.ui.main

import android.os.Bundle
import android.view.View
import com.example.weather_app.R
import com.example.weather_app.base.BaseActivity
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.utils.AnimationUtils.startSplashAnimation
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainActivityContract.Presenter>(),
    MainActivityContract.View {
    var mainActivityBinding: ActivityMainBinding? = null
    override val presenter: MainActivityContract.Presenter by inject()

    override fun getLayoutResource() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        mainActivityBinding = ActivityMainBinding.bind(view)
        presenter.attachView(this)

        mainActivityBinding?.startSplashAnimation(compositeDisposable)
        mainActivityBinding?.continueButton?.setOnClickListener {
            //TODO go to home activity
        }
    }

}