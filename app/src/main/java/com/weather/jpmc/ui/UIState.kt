package com.weather.jpmc.ui

import com.weather.jpmc.network.data.WeatherData

sealed class UIState {
    data class Loading(val loading: Boolean = false) : UIState()
    data class Success(val weatherData: WeatherData?) : UIState()
    data class Error(val title: String = "", val message: String) : UIState()
}
