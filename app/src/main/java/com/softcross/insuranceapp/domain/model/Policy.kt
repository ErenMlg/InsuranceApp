package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Policy(
    val policyNo: String = "",
    val customerNo: String,
    val policyAgent: String,
    val policyPrim: Int,
    val policyStatus: Char,
    val policyTypeCode: Int,
    val policyEnterDate: String,
    val policyStartDate: String? = null,
    val policyEndDate: String? = null
)