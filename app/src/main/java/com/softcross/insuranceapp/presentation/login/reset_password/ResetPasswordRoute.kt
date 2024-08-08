package com.softcross.insuranceapp.presentation.login.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.extensions.emailRegex
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme


@Composable
fun ResetPasswordRoute(
    modifier: Modifier = Modifier,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val uiState = viewModel.resetPasswordState.value
    val successMessage = stringResource(id = R.string.success_send_email)
    var email by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    when (uiState) {
        is ScreenState.Loading -> {
            snackbarMessage = ""
        }

        is ScreenState.Success -> {
            snackbarMessage = successMessage
            loading = false; isError = false
        }

        is ScreenState.Error -> {
            snackbarMessage = uiState.message
            loading = false; isError = true
        }
    }

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
                    errorMessage = stringResource(id = R.string.valid_email),
                    trailingIcon = {},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    regex = String::emailRegex
                )
                LoadingTextButton(
                    isLoading = loading,
                    isEnable = email.emailRegex(),
                    onClick = {
                        keyboardController?.hide()
                        loading = true
                        viewModel.resetPassword(email)
                    },
                    buttonText = R.string.send_link
                )
            }
        }
        if (snackbarMessage.isNotEmpty()) {
            CustomSnackbar(
                errorMessage = snackbarMessage,
                isError = isError,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun ResetPasswordHeader() {
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
