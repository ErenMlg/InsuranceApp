package com.softcross.insuranceapp.data.dto.address

import com.google.gson.annotations.SerializedName

data class ProvincesDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("districts")
    val districts: List<DistrictDto>
)