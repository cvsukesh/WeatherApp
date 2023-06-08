package com.weather.jpmc.network.data

data class NWResponse<out T>(val status: Status, val data: T?, val throwable: Throwable?) {
    companion object {
        fun <T> success(data: T): NWResponse<T> {
            return NWResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable): NWResponse<T> {
            return NWResponse(Status.ERROR, null, throwable)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR
}