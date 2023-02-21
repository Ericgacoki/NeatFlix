package com.ericg.neatflix.util

sealed class Resource<T>(val data: T? = null, val statusMessage: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(statusMessage = message)
}
