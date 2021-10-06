package com.example.weather_app.ui.home

import android.os.Bundle
import android.view.View
import com.example.weather_app.R
import com.example.weather_app.base.BaseActivity
import com.example.weather_app.databinding.ActivityHomeBinding
import com.example.weather_app.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity<HomeActivityContract.Presenter>(),
    HomeActivityContract.View{
    var homeActivityBinding: ActivityHomeBinding? = null
    override val presenter: HomeActivityContract.Presenter by inject()

    override fun getLayoutResource() = R.layout.activity_home

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        homeActivityBinding = ActivityHomeBinding.bind(view)
        presenter.attachView(this)

    }
}