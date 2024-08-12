package com.softcross.insuranceapp.domain.model


data class Payment(
    val creditCardNumber: String,
    val cardDate: String,
    val cardName: String,
    val cardCVC: String,
    val paymentDate:String,
    val paymentAmount:Int,
    val policyNo:String
)