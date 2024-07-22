package com.softcross.insuranceapp.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.extensions.emailRegex
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun LoginRoute(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .width((LocalConfiguration.current.screenWidthDp * 0.8).dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape),
            )
            CustomTextField(
                givenValue = "parturient",
                placeHolder = "quot",
                onValueChange = {},
                errorMessage = "pericula",
                trailingIcon = {},
                regex = String::emailRegex
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginRoutePreview() {
    InsuranceAppTheme() {
        LoginRoute()
    }
}
