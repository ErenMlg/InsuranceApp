package com.softcross.insuranceapp.data.dto.car_properties

import com.google.gson.annotations.SerializedName

data class MakesDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
