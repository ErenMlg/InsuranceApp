package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import retrofit2.http.FormUrlEncoded
import java.time.LocalDate
import java.util.Date

@Immutable
@Stable
data class Customer(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val birthdate: String = "",
    val email: String = "",
    val phone: String = "",
    val district: String = "",
    val city: String = "",
)