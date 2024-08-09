package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
enum class PolicyType(private val code: Int) {



    UNSELECTED(0),
    TRAFFIC(310),
    KASKO(340),
    HEALTH(610),
    DASK(199);

    fun getTypeCode(): Int {
        return code
    }
}

fun getPolicyByID(id: Int): String {
    return when (id) {
        310 -> "Traffic"
        340 -> "Kasko"
        610 -> "Health"
        199 -> "Dask"
        else -> "Unknown"
    }
}

fun getPolicyByName(name:String) : PolicyType{
    return when(name){
        "Traffic" -> PolicyType.TRAFFIC
        "Kasko" -> PolicyType.KASKO
        "Health" -> PolicyType.HEALTH
        "DASK" -> PolicyType.DASK
        else -> PolicyType.UNSELECTED
    }
}