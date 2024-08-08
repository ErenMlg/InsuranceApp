package com.softcross.insuranceapp.data.dto.health

import com.google.gson.annotations.SerializedName

data class HealthDto(
    @SerializedName("policyNo")
    val policyNo:String,
    @SerializedName("smoke")
    val smoke:Boolean,
    @SerializedName("alcohol")
    val alcohol:Boolean,
    @SerializedName("drugs")
    val drugs:Boolean,
    @SerializedName("sport")
    val sport:Boolean,
    @SerializedName("surgery")
    val surgery:Boolean,
    @SerializedName("allergy")
    val allergy:Boolean
)