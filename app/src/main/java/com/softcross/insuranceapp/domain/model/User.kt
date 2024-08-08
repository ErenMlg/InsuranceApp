package com.softcross.insuranceapp.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val userType: UserType = UserType.UNSPECIFIED
)

enum class UserType {
    UNSPECIFIED,
    ADMIN,
    USER
}
