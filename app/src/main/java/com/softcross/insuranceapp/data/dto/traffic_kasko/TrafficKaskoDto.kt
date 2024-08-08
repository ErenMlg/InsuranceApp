package com.softcross.insuranceapp.data.dto.traffic_kasko

import com.google.gson.annotations.SerializedName

data class TrafficKaskoDto(
    @SerializedName("policyNo")
    val policyNo: String,
    @SerializedName("plateProvinceCode")
    val plateProvinceCode: Int,
    @SerializedName("plateCode")
    val plateCode: String,
    @SerializedName("carMark")
    val carMark: String,
    @SerializedName("carModel")
    val carModel: String,
    @SerializedName("carModelYear")
    val modelYear: Int,
    @SerializedName("carMotorNo")
    val carMotorNo: String,
    @SerializedName("carChassisNo")
    val carChassisNo: String
)
