package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Province(
    val id: Int,
    val name: String,
    val districts: List<District>
)