package com.weather.jpmc

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.weather.jpmc.mvvm.MainViewModel
import com.weather.jpmc.mvvm.WeatherUseCase
import com.weather.jpmc.network.ApiService
import com.weather.jpmc.network.data.WeatherData
import com.weather.jpmc.repository.MockWeatherRepository
import com.weather.jpmc.utils.PreferenceManager
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var useCase: WeatherUseCase

    private lateinit var instrumentationContext: Context

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var preferenceManager: PreferenceManager

    private lateinit var repository: MockWeatherRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        repository = MockWeatherRepository(instrumentationContext, apiService)
        mainViewModel = MainViewModel(useCase, preferenceManager)
    }

    @Test
    fun test_fetch_weatherData() {
        repository.isSuccess = true
        runBlocking {
            val response = repository.makeApiCall("Dallas")
            Assert.assertTrue(response.data is WeatherData)
        }
    }
}