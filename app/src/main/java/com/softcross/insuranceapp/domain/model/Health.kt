package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Health(
    val policyNo:String,
    val smoke:Boolean,
    val alcohol:Boolean,
    val drugs:Boolean,
    val sport:Boolean,
    val surgery:Boolean,
    val allergy:Boolean
)
