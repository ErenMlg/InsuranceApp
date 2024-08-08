package com.softcross.insuranceapp.common.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
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

fun String.phoneRegex(): Boolean {
    val pattern =
        Pattern.compile("(^[0\\s]?[\\s]?)([(]?)([5])([0-9]{2})([)]?)([\\s]?)([0-9]{3})([\\s]?)([0-9]{2})([\\s]?)([0-9]{2})\$")
    return if (this.isNotEmpty()) {
        pattern.matcher(this).matches()
    } else {
        false
    }
}

fun String.idRegex(): Boolean {
    val pattern = Pattern.compile(
        "^[1-9]{1}[0-9]{9}[02468]{1}\$"
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

fun String.plateRegex(): Boolean {
    val pattern = Pattern.compile(
        "^([A-Z]\\d{4})|([A-Z]{2}\\d{3,4})|([A-Z]{3}\\d{2})$"
    )
    /*
    İkinci kısım 1,2 veya 3 harften oluşmaktadır. 2. kısımda
    1 harf varsa 3. kısımda 4 rakam,
    2 harf varsa 3 veya 4 rakam,
    3 harf varsa 2 rakam olur.
     */
    return if (this.isNotEmpty()) {
        return pattern.matcher(this).matches()
    } else {
        false
    }
}

fun String.plateCodeRegex(): Boolean {
    return if (this.isNotEmpty()) {
        return this.toInt() in 1..81
    } else {
        false
    }
}

fun String.motorNumberRegex(): Boolean {
    val pattern = Pattern.compile("^[A-Z0-9\\-]{5,20}$")
    // Valid "A1B2-C3D4"
    // Invalid "123/456"
    return if (this.isNotEmpty()) {
        return pattern.matcher(this).matches()
    } else {
        false
    }
}

fun String.chassisNumberRegex(): Boolean {
    val pattern = Pattern.compile("^[A-HJ-NPR-Z0-9]{17}$")
    // Valid 123456789ABCDEFG
    // Invalid 123456789*BCDEFG
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

fun String.uavtRegex(): Boolean {
    val pattern = Pattern.compile(
        "^[0-9]{10}\$"
    )
    return if (this.isNotEmpty()) {
        return pattern.matcher(this).matches()
    } else {
        false
    }
}

fun Long.toDate(format: String = "MM-dd-yyyy", locale: Locale = Locale.getDefault()): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(date)
}


fun String.formatBirthday(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    return LocalDate.parse(this, formatter)
}

fun String.calculateAge(): Long {
    try {
        val age = ChronoUnit.YEARS.between(
            this.formatBirthday(),
            LocalDate.now()
        )
        return if (age < 0) {
            0
        } else {
            age
        }
    } catch (e: Exception) {
        println(e.message)
        return -1
    }
}