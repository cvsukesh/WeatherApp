package com.weather.jpmc.repository

import android.content.Context
import com.weather.jpmc.mvvm.repo.WeatherRepository
import com.weather.jpmc.network.ApiService
import com.weather.jpmc.network.data.NWResponse
import com.weather.jpmc.network.data.WeatherData
import com.weather.jpmc.utils.AppTestUtils

class MockWeatherRepository(context: Context, apiService: ApiService) : WeatherRepository {

    var isSuccess = true

    override suspend fun makeApiCall(searchString: String): NWResponse<WeatherData> {
        val list: WeatherData =
            AppTestUtils.readJsonResponseFile("appConfig/nyc_high_school_list.json")
        return NWResponse.success(list)
    }
}