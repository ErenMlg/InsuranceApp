package com.softcross.insuranceapp.data.dto.address

import com.google.gson.annotations.SerializedName

data class AddressResponseDto(
    @SerializedName("status")
    val status : String,
    @SerializedName("data")
    val data : List<ProvincesDto>
)