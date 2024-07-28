package com.softcross.insuranceapp.common

sealed class ResponseState<out T : Any> {
    object Loading : ResponseState<Nothing>()
    data class Success<out T : Any>(val data: T) : ResponseState<T>()
    data class Error(val message: String) : ResponseState<Nothing>()
}

