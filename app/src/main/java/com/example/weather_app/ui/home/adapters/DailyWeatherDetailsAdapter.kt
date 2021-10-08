package com.example.weather_app.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.base.BaseViewHolder
import com.example.weather_app.data.db.enteties.DailyWeather
import com.example.weather_app.databinding.DailyWeatherCardLayoutBinding
import com.example.weather_app.utils.loadImage
import java.text.SimpleDateFormat
import java.util.*

class DailyWeatherDetailsAdapter() :
    RecyclerView.Adapter<BaseViewHolder>() {
    var dailyWeatherDetailsList = listOf<DailyWeather>()

    inner class ItemWeatherViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var itemWeatherBinding = DailyWeatherCardLayoutBinding.bind(itemView)

        fun bindView(dailyWeather: DailyWeather) {
            itemWeatherBinding.weatherIconImageView
                .loadImage("http://openweathermap.org/img/wn/${dailyWeather.weather?.firstOrNull()?.icon}.png")
            itemWeatherBinding.tempDegreeTextView.text = "${dailyWeather.temp?.day} °C"
            itemWeatherBinding.tempMinMaxTextView.text = "${dailyWeather.temp?.max}°C / ${dailyWeather.temp?.min}°C"
            itemWeatherBinding.dayTextView.text = SimpleDateFormat("EEEE").format(Date(dailyWeather.date))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemWeatherViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.daily_weather_card_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is ItemWeatherViewHolder -> {
                holder.bindView(dailyWeatherDetailsList[position])
            }
        }
    }

    override fun getItemCount() = dailyWeatherDetailsList.size

    fun swapData(newList: List<DailyWeather>) {
        dailyWeatherDetailsList = newList
        notifyDataSetChanged()
    }
}