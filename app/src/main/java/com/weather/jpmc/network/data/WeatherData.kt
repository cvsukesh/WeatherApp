package com.weather.jpmc.network.data

data class WeatherData(
    val coord: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: String
)

data class Coordinates(val lon: Double, val lat: Double)

data class Weather(val id: Int, val main: String, val description: String, val icon: String)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val humidity: Int
)

data class Wind(val speed: Double, val deg: Double, val gust: Double)

data class Clouds(val all: Int)

data class Sys(
    val type: Int,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

