package com.softcross.insuranceapp.data.dto.dask

import com.google.gson.annotations.SerializedName

data class DaskDto(
    @SerializedName("policyNo")
    val policyNo: String,
    @SerializedName("uavt")
    val uavt: String,
    @SerializedName("apartmentMeter")
    val apartmentMeter: Float,
    @SerializedName("apartmentFloor")
    val apartmentFloor: Int,
    @SerializedName("apartmentAge")
    val apartmentAge: Int,
    @SerializedName("structType")
    val apartmentType: String,
)
