package com.softcross.insuranceapp.presentation.customer.edit_customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.AllLocations
import com.softcross.insuranceapp.common.PhoneNumberVisualTransformation
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.extensions.dateTimeToDate
import com.softcross.insuranceapp.common.extensions.emailRegex
import com.softcross.insuranceapp.common.extensions.idRegex
import com.softcross.insuranceapp.common.extensions.nameSurnameRegex
import com.softcross.insuranceapp.common.extensions.phoneRegex
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.District
import com.softcross.insuranceapp.domain.model.Province
import com.softcross.insuranceapp.presentation.components.CustomDateTimePicker
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingIconButton
import kotlinx.coroutines.delay

@Composable
fun EditCustomerRoute(
    viewModel: EditCustomerViewModel = hiltViewModel(),
    onHome: () -> Unit
) {
    var snackbarMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val uiState = viewModel.newCustomerState.value
    val oldCustomerState = viewModel.oldCustomerState.value

    LaunchedEffect(key1 = snackbarMessage) {
        if (snackbarMessage.isNotEmpty() && !isError) {
            delay(2000)
            onHome()
        }
    }

    when (uiState) {
        is ScreenState.Loading -> {
            snackbarMessage = ""
        }

        is ScreenState.Success -> {
            isError = false
            snackbarMessage = stringResource(id = R.string.success_add_customer)

        }

        is ScreenState.Error -> {
            isError = true
            snackbarMessage = uiState.message
        }
    }

    Box {
        Column(
            modifier = Modifier
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
            if (oldCustomerState is ScreenState.Success) {
                NewCustomerForm(
                    oldCustomer = oldCustomerState.data,
                    onCreate = { viewModel.updateCustomer(it) },
                    provinceList = AllLocations.getLocations()
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
fun NewCustomerForm(
    oldCustomer: Customer,
    onCreate: (Customer) -> Unit,
    provinceList: List<Province>
) {
    var idNumber by remember { mutableStateOf(oldCustomer.id) }
    var name by remember { mutableStateOf(oldCustomer.name) }
    var surname by remember { mutableStateOf(oldCustomer.surname) }
    var birthdate by remember { mutableStateOf(oldCustomer.birthdate) }
    var city by remember {
        mutableStateOf<Province?>(
            AllLocations.getLocations().find { it.name == oldCustomer.city })
    }
    var district by remember { mutableStateOf<District?>(city?.districts?.find { it.name == oldCustomer.district }) }
    var phoneNumber by remember { mutableStateOf(oldCustomer.phone) }
    var email by remember { mutableStateOf(oldCustomer.email) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .verticalScroll(rememberScrollState())
    ) {
        CustomTextField(
            givenValue = idNumber,
            placeHolder = stringResource(id = R.string.field_id_number),
            onValueChange = { if (it.length <= 11) idNumber = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = String::idRegex,
            errorMessage = stringResource(id = R.string.valid_id)
        )
        Row(Modifier.fillMaxWidth()) {
            CustomTextField(
                givenValue = name,
                placeHolder = stringResource(id = R.string.field_name),
                onValueChange = { name = it },
                regex = String::nameSurnameRegex,
                errorMessage = stringResource(id = R.string.valid_name),
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 8.dp)
            )
            CustomTextField(
                givenValue = surname,
                placeHolder = stringResource(id = R.string.field_surname),
                onValueChange = { surname = it },
                regex = String::nameSurnameRegex,
                errorMessage = stringResource(id = R.string.valid_surname),
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp)
            )
        }
        CustomDateTimePicker(
            placeHolder = "Birthday",
            onDateSelected = { birthdate = it },
            selectedDate = birthdate.dateTimeToDate()
        )
        CustomSelectionDialog(
            data = provinceList.map { it.name },
            placeHolder = "City",
            onDataSelected = { data ->
                city = provinceList.find { it.name == data }
            },
            title = "Please select a city",
            selected = city?.name ?: "Unknown"
        )
        CustomSelectionDialog(
            data = city?.districts?.map { it.name } ?: emptyList(),
            placeHolder = "District",
            onDataSelected = { data ->
                district = city?.districts?.find { district -> district.name == data }
            },
            enabled = city != null,
            title = "Please select a district",
            selected = district?.name ?: "Unknown"
        )
        CustomTextField(
            givenValue = phoneNumber,
            visualTransformation = PhoneNumberVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            placeHolder = stringResource(id = R.string.field_phone),
            onValueChange = { if (it.length <= 11) phoneNumber = it },
            regex = String::phoneRegex,
            errorMessage = stringResource(id = R.string.valid_phone)
        )
        CustomTextField(
            givenValue = email,
            placeHolder = stringResource(id = R.string.field_email),
            onValueChange = { email = it },
            regex = String::emailRegex,
            errorMessage = stringResource(id = R.string.valid_email)
        )
        LoadingIconButton(
            isLoading = false,
            isEnable = true,
            onClick = {
                keyboardController?.hide()
                onCreate(
                    Customer(
                        id = idNumber,
                        name = name,
                        surname = surname,
                        birthdate = birthdate,
                        email = email,
                        phone = phoneNumber,
                        district = district?.name ?: "Unknown",
                        city = city?.name ?: "Unknown"
                    )
                )
            },
            buttonText = R.string.new_customer,
            id = R.drawable.icon_add_customer
        )
    }
}