package com.example.weather_app.base

sealed class DataResult<T> {
    class Success<T>(val dataResponse: T) : DataResult<T>()
    class Failure<T>(val error: String) : DataResult<T>()
}