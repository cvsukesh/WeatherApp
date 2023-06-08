package com.weather.jpmc.network

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.weather.jpmc.R
import com.weather.jpmc.network.data.NWResponse
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseDataSource(open val context: Context) {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): NWResponse<T> {
        val response = call()
        if (!isInternetAvailable(context)) {
            return NWResponse.error(NetworkErrorException(context.resources.getString(R.string.network_error_message)))
        }
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return NWResponse.success(body)
        }
        return NWResponse.error(HttpException(response))
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }
}