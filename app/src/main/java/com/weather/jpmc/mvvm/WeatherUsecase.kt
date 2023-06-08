package com.weather.jpmc.mvvm

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.weather.jpmc.mvvm.repo.WeatherRepository
import com.weather.jpmc.network.data.NWResponse
import com.weather.jpmc.network.data.WeatherData
import java.util.*
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    val context: Context,
    val repository: WeatherRepository
) {

    suspend fun getCurrentLocationWeather(): NWResponse<WeatherData> {
        var currentLocation = ""
        return try {
            currentLocation = getCurrentLocation()
            repository.makeApiCall(currentLocation)
        } catch (ex: Exception) {
            NWResponse.error(ex)
        }
    }

    suspend fun makeSearchApi(searchString: String): NWResponse<WeatherData> {
        return repository.makeApiCall(searchString)
    }

    @Throws(java.lang.Exception::class)
    private fun getCurrentLocation(): String {
        var currentCity = ""
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // get GPS status
        val checkGPS = locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER)

        val location = if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.getLastKnownLocation(
                if (checkGPS) {
                    LocationManager.GPS_PROVIDER
                } else {
                    LocationManager.NETWORK_PROVIDER
                }
            )
        } else null

        val geocoder = Geocoder(context, Locale.getDefault())
        location?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    it.latitude,
                    it.longitude,
                    1
                ) { addresses ->
                    currentCity = addresses[0]?.locality + "," + addresses[0]?.countryCode
                }
            } else {
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                currentCity = addresses?.get(0)?.locality + "," + addresses?.get(0)?.countryCode
            }
        }
        return currentCity
    }
}