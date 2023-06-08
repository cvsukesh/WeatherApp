package com.weather.jpmc.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.jpmc.network.data.Status
import com.weather.jpmc.ui.UIState
import com.weather.jpmc.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: WeatherUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _weatherData: MutableLiveData<UIState> = MutableLiveData()
    val weatherData: LiveData<UIState> = _weatherData

    private val _toolBarTitle: MutableLiveData<String> = MutableLiveData()
    val toolBarTitle: LiveData<String> = _toolBarTitle

    fun getCurrentOrCachedWeatherLocation() {
        viewModelScope.launch {
            val searchFromStringPrefs = preferenceManager.getStringValueFromPreferences(
                SEARCH_STRING
            )
            val response = if (searchFromStringPrefs.isNullOrBlank()) {
                useCase.getCurrentLocationWeather()
            } else {
                useCase.makeSearchApi(searchFromStringPrefs)
            }

            _weatherData.postValue(
                when (response.status) {
                    Status.SUCCESS -> {
                        UIState.Success(response.data)
                    }
                    Status.ERROR -> {
                        UIState.Error(
                            "Error",
                            "There is an issue while retrieving the data! Please try again"
                        )
                    }
                }
            )
        }
    }

    fun searchWeatherData(searchString: String) {
        viewModelScope.launch {
            val response = useCase.makeSearchApi(searchString)
            _weatherData.postValue(
                when (response.status) {
                    Status.SUCCESS -> {
                        preferenceManager.storeStringInPreferences(SEARCH_STRING, searchString)
                        UIState.Success(response.data)
                    }
                    Status.ERROR -> {
                        UIState.Error(
                            "Error",
                            "There is an issue while retrieving the data! Please try again"
                        )
                    }
                }
            )
        }
    }

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.postValue(title)
    }

    companion object {
        const val SEARCH_STRING = "SEARCH_STRING"
    }
}