package com.softcross.insuranceapp.data.dto.policy

import com.google.gson.annotations.SerializedName

data class PolicyDto(
    @SerializedName("customerNo")
    val customerNo: String,
    @SerializedName("policyAgent")
    val policyAgent: String,
    @SerializedName("policyEndDate")
    val policyEndDate: String,
    @SerializedName("policyEnterDate")
    val policyEnterDate: String,
    @SerializedName("policyNo")
    val policyNo: String,
    @SerializedName("policyPrim")
    val policyPrim: Int,
    @SerializedName("policyStartDate")
    val policyStartDate: String,
    @SerializedName("policyStatus")
    val policyStatus: Char,
    @SerializedName("policyTypeCode")
    val policyTypeCode: Int
)