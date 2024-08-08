package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Immutable
@Stable
data class Dask(
    val policyNo: String,
    val uavt: String,
    val apartmentMeter: Float,
    val apartmentFloor: Int,
    val apartmentAge: Int,
    val apartmentType: String
)
