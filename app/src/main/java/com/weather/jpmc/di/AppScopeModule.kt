package com.weather.jpmc.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.weather.jpmc.mvvm.WeatherUseCase
import com.weather.jpmc.mvvm.repo.WeatherRepository
import com.weather.jpmc.mvvm.repo.WeatherRepositoryIMPL
import com.weather.jpmc.network.ApiService
import com.weather.jpmc.network.RequestInterceptor
import com.weather.jpmc.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class AppScopeModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun provideWeatherUseCase(
        @ApplicationContext context: Context,
        weatherRepository: WeatherRepository
    ) = WeatherUseCase(context, weatherRepository)

    @Provides
    fun provideNetworkInterceptor(): RequestInterceptor = RequestInterceptor()

    @Provides
    fun provideOkHttpClient(interceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(TIMEOUT_30, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_30, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideWeatherRepository(
        @ApplicationContext context: Context,
        apiService: ApiService
    ): WeatherRepository = WeatherRepositoryIMPL(context, apiService)

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManager(context)

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val TIMEOUT_30 = 30L
    }
}

