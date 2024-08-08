package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Immutable
@Stable
data class Makes(
    val id: Int,
    val name: String
)
