package com.softcross.insuranceapp.data.dto.traffic_kasko

import com.google.gson.annotations.SerializedName

data class TrafficKaskoResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<TrafficKaskoResponse>
)
