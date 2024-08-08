package com.softcross.insuranceapp.data.dto.car_properties

import com.google.gson.annotations.SerializedName

data class ModelsResponse(
    @SerializedName("data")
    val data: List<ModelsDto>
)