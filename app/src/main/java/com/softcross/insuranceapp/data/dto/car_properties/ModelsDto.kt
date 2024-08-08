package com.softcross.insuranceapp.data.dto.car_properties

import com.google.gson.annotations.SerializedName

data class ModelsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("make_id")
    val make_id: Int,
    @SerializedName("name")
    val name: String
)
