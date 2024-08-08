package com.softcross.insuranceapp.data.dto.policy

import com.google.gson.annotations.SerializedName

data class PolicyResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<PolicyDto>
)