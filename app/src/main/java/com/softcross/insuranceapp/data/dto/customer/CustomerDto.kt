package com.softcross.insuranceapp.data.dto.customer

import com.google.gson.annotations.SerializedName

data class CustomerDto(
    @SerializedName("customerID")
    val id: String,
    @SerializedName("customerName")
    val name: String,
    @SerializedName("customerSurname")
    val surname: String,
    @SerializedName("customerBirthdate")
    val birthdate: String,
    @SerializedName("customerEmail")
    val email: String,
    @SerializedName("customerPhone")
    val phone: String,
    @SerializedName("customerDistrict")
    val district: String,
    @SerializedName("customerProvince")
    val city: String
)