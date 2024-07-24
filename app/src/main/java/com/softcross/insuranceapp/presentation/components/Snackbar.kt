package com.softcross.insuranceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R

@Composable
fun CustomSnackbar(
    errorMessage: String,
    modifier: Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = errorMessage) {
        snackbarHostState.showSnackbar(errorMessage)
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
            .background(MaterialTheme.colorScheme.error)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = errorMessage,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}