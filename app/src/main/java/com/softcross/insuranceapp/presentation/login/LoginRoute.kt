package com.softcross.insuranceapp.presentation.login

import android.content.Context
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.CurrentUser
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.extensions.emailRegex
import com.softcross.insuranceapp.common.extensions.noRippleClickable
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.presentation.components.CustomPasswordTextField
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onLogin: () -> Unit,
    onResetPassword: () -> Unit
) {
    val uiState = viewModel.loginUiState.value
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    when (uiState) {
        is ScreenState.Loading -> {
            snackbarMessage = ""
        }
        is ScreenState.Success -> {
            CurrentUser.setCurrentUser(uiState.data)
            if (checked) {
                context.getSharedPreferences("logFile", Context.MODE_PRIVATE).edit()
                    .putBoolean("stayLogged", true).apply()
                context.getSharedPreferences("logFile", Context.MODE_PRIVATE).edit()
                    .putString("userID", CurrentUser.getCurrentUserID()).apply()
            }
            onLogin()
        }
        is ScreenState.Error -> {
            loading = false
            snackbarMessage = uiState.message
        }
    }

    Box{
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()
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
                CustomPasswordTextField(
                    givenValue = password,
                    placeHolder = stringResource(id = R.string.enter_password),
                    onValueChange = { password = it }
                )
                LoadingTextButton(
                    isLoading = loading,
                    isEnable = email.emailRegex() && password.passwordRegex(),
                    onClick = {
                        keyboardController?.hide()
                        loading = true
                        viewModel.loginUser(email, password)
                    },
                    buttonText = R.string.login
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            checked = !checked
                        },
                        modifier = Modifier.size(24.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    CustomText(
                        text = "Stay logged",
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    CustomText(
                        text = stringResource(id = R.string.forgot_password),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .noRippleClickable { onResetPassword() }
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
        if (snackbarMessage.isNotEmpty()) {
            CustomSnackbar(
                errorMessage = snackbarMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun LoginHeader() {
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
            text = stringResource(id = R.string.welcome_login),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            line = 2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}