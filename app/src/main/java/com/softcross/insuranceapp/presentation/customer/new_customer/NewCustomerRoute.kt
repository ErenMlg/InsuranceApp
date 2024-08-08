package com.softcross.insuranceapp.presentation.customer.new_customer

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.PhoneNumberVisualTransformation
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.extensions.emailRegex
import com.softcross.insuranceapp.common.extensions.idRegex
import com.softcross.insuranceapp.common.extensions.nameSurnameRegex
import com.softcross.insuranceapp.common.extensions.phoneRegex
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.District
import com.softcross.insuranceapp.domain.model.Province
import com.softcross.insuranceapp.presentation.components.CustomDateTimePicker
import com.softcross.insuranceapp.presentation.components.LoadingIconButton
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun NewCustomerRoute(
    modifier: Modifier = Modifier,
    viewModel: NewCustomerViewModel = hiltViewModel()
) {
    if (viewModel.newCustomerState.value is ScreenState.Success) {
        Toast.makeText(
            LocalContext.current,
            "Customer added successfully",
            Toast.LENGTH_SHORT
        ).show()
    }

    if (viewModel.newCustomerState.value is ScreenState.Error) {
        Toast.makeText(
            LocalContext.current,
            "An error occurred while adding the customer",
            Toast.LENGTH_SHORT
        ).show()
    }

    //Adres çekmeyi uygulama açıldığında yapacağız.
    when (val provinceState = viewModel.addressState.value) {
        is ScreenState.Loading -> {
            // Loading
        }

        is ScreenState.Success -> {
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
                NewCustomerForm({
                    viewModel.addCustomer(it)
                }, provinceState.data)
            }
        }

        is ScreenState.Error -> {
            // Error
        }
    }

}

@Composable
fun NewCustomerForm(
    onCreate: (Customer) -> Unit,
    provinceList: List<Province>
) {
    var idNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var city by remember { mutableStateOf<Province?>(null) }
    var district by remember { mutableStateOf<District?>(null) }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
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
            onDateSelected = { birthdate = it }
        )
        CustomSelectionDialog(
            data = provinceList.map { it.name },
            placeHolder = "City",
            onDataSelected = { data ->
                city = provinceList.find { it.name == data }
            },
            title = "Please select a city"
        )
        CustomSelectionDialog(
            data = city?.districts?.map { it.name } ?: emptyList(),
            placeHolder = "District",
            onDataSelected = { data ->
                district = city?.districts?.find { district -> district.name == data }
            },
            enabled = city != null,
            title = "Please select a district"
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewCustomerRoutePreview() {
    InsuranceAppTheme {
        NewCustomerRoute()
    }
}