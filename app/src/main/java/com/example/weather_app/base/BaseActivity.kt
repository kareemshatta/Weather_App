package com.example.weather_app.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.R

abstract class BaseActivity<P> : AppCompatActivity(), MvpViewUtils {
    protected abstract val presenter: P

    protected abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(getLayoutResource(), null, false)
        setContentView(view)
        initViews(savedInstanceState, view)
    }

    protected abstract fun initViews(savedInstanceState: Bundle?, view: View)

    override fun onNetworkError() {
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }
}