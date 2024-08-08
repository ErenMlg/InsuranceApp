package com.softcross.insuranceapp.domain.model

enum class PolicyStatus(private val code: Char) {
    OFFER('T'),
    POLICY('P');

    fun getStatusCode(): Char {
        return code
    }
}

fun getPolicyStatusByCode(code: Char): String {
    return when (code) {
        'T' -> "Offer"
        'P' -> "Policy"
        else -> "Unknown"
    }
}