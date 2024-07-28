package com.softcross.insuranceapp.domain.model

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
