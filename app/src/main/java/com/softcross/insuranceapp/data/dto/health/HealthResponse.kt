package com.softcross.insuranceapp.data.dto.health

import com.google.gson.annotations.SerializedName

data class HealthResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<HealthDto>
)
