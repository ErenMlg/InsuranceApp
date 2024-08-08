package com.softcross.insuranceapp.data.dto.customer

import com.google.gson.annotations.SerializedName

data class CustomerResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<CustomerDto>
)