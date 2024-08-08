package com.softcross.insuranceapp.common.extensions

import retrofit2.HttpException

fun Exception.httpErrorHandle(): Exception {
    if (this is HttpException) {
        val errorMessage =
            this.response()?.errorBody()?.string()?.substringAfter("message\":\"")
                ?.substringBefore("\"")
        return Exception(errorMessage)
    } else {
        return this
    }
}