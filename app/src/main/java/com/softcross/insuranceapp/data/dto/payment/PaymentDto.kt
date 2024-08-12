package com.softcross.insuranceapp.data.dto.payment

import com.google.gson.annotations.SerializedName

data class PaymentDto(
    @SerializedName("creditCardNumber")
    val creditCardNumber: String,
    @SerializedName("creditCardDate")
    val cardDate: String,
    @SerializedName("creditCardName")
    val cardName: String,
    @SerializedName("creditCardCVC")
    val cardCVC: String,
    @SerializedName("paymentDate")
    val paymentDate:String,
    @SerializedName("paymentAmount")
    val paymentAmount:Int,
    @SerializedName("policyNo")
    val policyNo:String
)
