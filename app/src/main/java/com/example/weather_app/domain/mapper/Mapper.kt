package com.example.weather_app.domain.mapper

import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.models.CityDataResponse
import com.example.weather_app.models.CityDetails
import com.example.weather_app.models.WeatherDetailsResponse

fun CityDataResponse.convertToCityDetailsModel(): CityDetails {
    return CityDetails(
        latitude = coord.lat,
        longitude = coord.lon,
        cityName = "$name ,${sys.country}"
    )
}

fun WeatherDetailsResponse.convertToWeatherEntityModel(cityDetails: CityDetails): WeatherEntity {
    return WeatherEntity(
        latitude = lat,
        longitude = lon,
        placeName = cityDetails.cityName,
        current = current,
        daily = daily
    )
}