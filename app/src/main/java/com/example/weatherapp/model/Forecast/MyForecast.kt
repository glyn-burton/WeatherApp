package com.example.weatherapp.model.Forecast

data class MyForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Int
)