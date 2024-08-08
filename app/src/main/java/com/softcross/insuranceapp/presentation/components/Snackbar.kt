package com.softcross.insuranceapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R

@Composable
fun CustomSnackbar(
    errorMessage: String,
    modifier: Modifier,
    isError: Boolean = true
) {
    val drawable = if (isError) R.drawable.icon_close else R.drawable.icon_success
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = errorMessage) {
        snackbarHostState.showSnackbar(
            message = errorMessage
        )
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isError) Modifier.background(MaterialTheme.colorScheme.error)
                else Modifier.background(MaterialTheme.colorScheme.onSurface)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.background
            )
            CustomText(
                modifier = Modifier
                    .padding(8.dp),
                text = errorMessage,
                color = Color.White,
                fontFamilyID = R.font.poppins_regular,
                fontSize = 12.sp
            )
        }
    }
}