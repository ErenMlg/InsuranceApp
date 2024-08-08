package com.softcross.insuranceapp.data.dto.address

import com.google.gson.annotations.SerializedName

data class DistrictDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)