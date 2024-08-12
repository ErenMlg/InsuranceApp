package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Model(
    val id: Int,
    val make_id: Int,
    val name: String
)
