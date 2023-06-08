package com.weather.jpmc.mvvm.repo

import com.weather.jpmc.network.data.NWResponse
import com.weather.jpmc.network.data.WeatherData

interface WeatherRepository {

    suspend fun makeApiCall(searchString: String) : NWResponse<WeatherData>
}