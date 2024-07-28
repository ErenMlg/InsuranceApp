package com.softcross.insuranceapp.presentation.customer.new_customer

import android.telephony.PhoneNumberUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.PhoneNumberVisualTransformation
import com.softcross.insuranceapp.common.extensions.nameSurnameRegex
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.common.extensions.phoneRegex
import com.softcross.insuranceapp.presentation.components.CustomDateTimePicker
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun NewCustomerRoute(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomText(
            text = stringResource(id = R.string.new_customer),
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
        NewCustomerForm()
    }
}

@Composable
fun NewCustomerForm() {
    var idNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(0.9f)
            .verticalScroll(rememberScrollState())
    ) {
        CustomTextField(
            givenValue = idNumber,
            placeHolder = stringResource(id = R.string.field_id_number),
            onValueChange = { idNumber = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = name,
            placeHolder = stringResource(id = R.string.field_name),
            onValueChange = { name = it },
            regex = String::nameSurnameRegex
        )
        CustomTextField(
            givenValue = surname,
            placeHolder = stringResource(id = R.string.field_surname),
            onValueChange = { surname = it },
            regex = String::nameSurnameRegex
        )
        CustomDateTimePicker(
            placeHolder = "Birthday",
        )
        CustomTextField(
            givenValue = email,
            placeHolder = "City",
            onValueChange = { email = it },
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = "",
            placeHolder = "District",
            onValueChange = {},
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = phoneNumber,
            visualTransformation = PhoneNumberVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            placeHolder = stringResource(id = R.string.field_phone),
            onValueChange = { if (it.length <= 11) phoneNumber = it },
            regex = String::phoneRegex
        )
        CustomTextField(
            givenValue = email,
            placeHolder = stringResource(id = R.string.field_email),
            onValueChange = { email = it },
            regex = String::passwordRegex
        )
        LoadingTextButton(
            isLoading = false,
            isEnable = true,
            onClick = { /*TODO*/ },
            buttonText = R.string.new_customer
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewCustomerRoutePreview() {
    InsuranceAppTheme {
        NewCustomerRoute()
    }
}