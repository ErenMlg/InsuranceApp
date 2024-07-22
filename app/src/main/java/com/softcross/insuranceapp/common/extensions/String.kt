package com.softcross.insuranceapp.common.extensions

import java.util.regex.Pattern

fun String.emailRegex(): Boolean {
    val pattern = Pattern.compile(
        "^[A-Za-z](?=\\S+\$)(.*)([@]{1})(?=\\S+\$)(.{1,})(\\.)(.{1,})"
    )
    return if (this.isNotEmpty()) {
        pattern.matcher(this).matches()
    } else {
        false
    }
}

fun String.nameSurnameRegex(): Boolean {
    val pattern = Pattern.compile(
        "^[a-zA-Z]{2,}\$"
    )
    return if (this.isNotEmpty()) {
        return pattern.matcher(this).matches()
    } else {
        false
    }
}

fun String.passwordRegex(): Boolean {
    val pattern = Pattern.compile(
        "^(?=.*[0-9])(?=\\S+\$)(?=.*[a-z])(?=.*[A-Z]).{8,}\$"
    )
    return if (this.isNotEmpty()) {
        return pattern.matcher(this).matches()
    } else {
        false
    }
}