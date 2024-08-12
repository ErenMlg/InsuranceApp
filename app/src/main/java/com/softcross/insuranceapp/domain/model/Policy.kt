package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Policy(
    val policyNo: String = "",
    val customerNo: String = "",
    val policyAgent: String = "",
    val policyPrim: Int = 0,
    val policyStatus: Char = ' ',
    val policyTypeCode: Int = 0,
    val policyEnterDate: String = "",
    val policyStartDate: String? = null,
    val policyEndDate: String? = null
)