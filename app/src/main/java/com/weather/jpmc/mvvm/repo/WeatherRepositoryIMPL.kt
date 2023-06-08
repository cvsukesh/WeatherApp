package com.weather.jpmc.mvvm.repo

import android.content.Context
import com.weather.jpmc.network.ApiService
import com.weather.jpmc.network.BaseDataSource
import com.weather.jpmc.network.data.NWResponse
import com.weather.jpmc.network.data.WeatherData
import javax.inject.Inject

class WeatherRepositoryIMPL @Inject constructor(
    override val context: Context,
    val apiService: ApiService
) : BaseDataSource(context), WeatherRepository {

    override suspend fun makeApiCall(searchString: String): NWResponse<WeatherData> {
        return getResult {
            apiService.searchWeatherData(searchString)
        }
    }
}