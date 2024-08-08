package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Kasko(
    val policyNo: String = "",
    val plate: Int,
    val plateCode: String,
    val make: String,
    val model: String,
    val year: Int,
    val chassisNo: String,
    val engineNo: String,
)