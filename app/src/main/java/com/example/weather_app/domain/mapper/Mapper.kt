package com.example.weather_app.domain.mapper

import com.example.weather_app.models.CityDataResponse
import com.example.weather_app.models.CityDetails

fun CityDataResponse.convertToCityDetailsModel(): CityDetails {
    return CityDetails(
        latitude = coord.lat,
        longitude = coord.lon,
        country = sys.country,
        cityName = name
    )
}