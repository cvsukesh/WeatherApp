package com.weather.jpmc.network

import com.weather.jpmc.network.data.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun searchWeatherData(
        @Query("q") search: String
    ): Response<WeatherData>


    //https://api.openweathermap.org/data/2.5/weather?q=Dallas&appid=d52b0b4bbcb5e3e7dc4b78aec8b814d5
    //@Query("appid") appId: String = "d52b0b4bbcb5e3e7dc4b78aec8b814d5"
}