package com.softcross.insuranceapp.presentation.login.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.extensions.emailRegex
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.presentation.components.CustomPasswordTextField
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.login.LoginRoute
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme


@Composable
fun ResetPasswordRoute(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResetPasswordHeader()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 48.dp)
            ) {
                CustomTextField(
                    givenValue = email,
                    placeHolder = stringResource(id = R.string.enter_email),
                    onValueChange = { email = it },
                    errorMessage = stringResource(id = R.string.email_validation_error),
                    trailingIcon = {},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    regex = String::emailRegex
                )
                LoadingTextButton(
                    isLoading = loading,
                    isEnable = email.emailRegex(),
                    onClick = {
                        loading = true
                    },
                    buttonText = R.string.send_link
                )
            }
        }
        if (errorMessage.isNotEmpty()) {
            CustomSnackbar(
                errorMessage = errorMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
            )
            errorMessage = ""
        }
    }
}

@Composable
fun ResetPasswordHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .padding(16.dp)
                .size(160.dp)
                .clip(CircleShape),
        )
        CustomText(
            text = stringResource(id = R.string.app_name),
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        CustomText(
            text = stringResource(id = R.string.reset_password),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            line = 2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginRoutePreview() {
    InsuranceAppTheme {
        ResetPasswordRoute()
    }
}
