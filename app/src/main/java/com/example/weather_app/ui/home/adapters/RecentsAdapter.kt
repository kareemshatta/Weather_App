package com.example.weather_app.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.base.BaseViewHolder
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.databinding.RecentCardLayoutBinding

class RecentsAdapter(val recentAdapterCallback: RecentAdapterCallback) :
    RecyclerView.Adapter<BaseViewHolder>() {
    var recentList = listOf<WeatherEntity>()

    interface RecentAdapterCallback {
        fun onDeleteRecentItem(cityID: Int)
    }

    inner class RecentViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var itemWeatherBinding = RecentCardLayoutBinding.bind(itemView)

        fun bindView(weatherEntity: WeatherEntity) {
            itemWeatherBinding.cityTextView.text = weatherEntity.placeName

            itemWeatherBinding.deleteImageView.setOnClickListener {
                recentAdapterCallback.onDeleteRecentItem(weatherEntity.placeId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return RecentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recent_card_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is RecentViewHolder -> {
                holder.bindView(recentList[position])
            }
        }
    }

    override fun getItemCount() = recentList.size

    fun swapData(newList: List<WeatherEntity>) {
        recentList = newList
        notifyDataSetChanged()
    }

}