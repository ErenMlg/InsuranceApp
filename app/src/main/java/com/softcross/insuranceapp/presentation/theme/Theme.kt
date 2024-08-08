package com.softcross.insuranceapp.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.Dimension
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkRed,
    secondary = DarkGray,
    tertiary = DarkBlack,
    surfaceVariant = DarkWhite,
    background = DarkWhite,
    error = DarkRed,
    onSurface = SuccessColor
)

private val LightColorScheme = lightColorScheme(
    primary = LightRed,
    secondary = LightGray,
    tertiary = LightBlack,
    surfaceVariant = LightSubWhite,
    background = LightWhite,
    surface = LightRed,
    error = LightRed,
    onSurface = SuccessColor
)


@Composable
fun InsuranceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}