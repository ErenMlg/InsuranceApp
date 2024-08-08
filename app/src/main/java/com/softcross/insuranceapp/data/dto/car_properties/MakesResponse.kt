package com.softcross.insuranceapp.data.dto.car_properties

import com.google.gson.annotations.SerializedName

data class MakesResponse(
    @SerializedName("data")
    val data: List<MakesDto>
)