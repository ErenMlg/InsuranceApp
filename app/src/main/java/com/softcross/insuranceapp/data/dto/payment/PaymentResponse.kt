package com.softcross.insuranceapp.data.dto.payment

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<PaymentDto>
)
