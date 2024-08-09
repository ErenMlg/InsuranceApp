package com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.TempVariables
import com.softcross.insuranceapp.common.extensions.calculateAge
import com.softcross.insuranceapp.common.extensions.chassisNumberRegex
import com.softcross.insuranceapp.common.extensions.motorNumberRegex
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.common.extensions.plateCodeRegex
import com.softcross.insuranceapp.common.extensions.plateRegex
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Models
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.domain.model.Traffic
import com.softcross.insuranceapp.presentation.components.CustomLargeIconButton
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import java.time.Year

@Composable
fun TrafficPolicyForm(
    modelState: ScreenState<List<Models>>,
    customer: Customer,
    modelSearch: (Int, Int) -> Unit,
    onTakeOfferClick: (Int, Int) -> Unit,
    onSetPolicyClick: (Policy) -> Unit,
    onCarCreate: (Traffic) -> Unit,
    addedPolicy: Policy?,
) {
    val makesList = TempVariables.makeList
    var plateCode by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var motorNumber by remember { mutableStateOf("") }
    var chassisNumber by remember { mutableStateOf("") }
    var selectedMake by remember { mutableStateOf("") }
    var selectedYear by remember { mutableIntStateOf(0) }
    var selectedModel by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(0) }
    val selectedMakeID by remember {
        derivedStateOf { makesList.find { makes -> makes.name == selectedMake }?.id ?: 0 }
    }

    LaunchedEffect(key1 = selectedMakeID, key2 = selectedYear) {
        selectedModel = ""
        if (selectedMakeID != 0 && selectedYear != 0) {
            modelSearch(selectedMakeID, selectedYear)
        }
    }

    LaunchedEffect(key1 = addedPolicy) {
        if (addedPolicy != null) {
            println("Selamın Aleyküm")
            onCarCreate(
                Traffic(
                    policyNo = addedPolicy.policyNo,
                    plate = plateCode.toIntOrNull() ?: 0,
                    plateCode = plateNumber,
                    make = selectedMake,
                    model = selectedModel,
                    year = selectedYear,
                    chassisNo = chassisNumber,
                    engineNo = motorNumber
                )
            )
        }
    }

    Column {
        CustomText(
            text = "Traffic",
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            CustomTextField(
                givenValue = plateCode,
                placeHolder = "Plate Code",
                onValueChange = { if (it.length <= 2) plateCode = it },
                errorMessage = "Plate code must be between 1 and 81",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                regex = String::plateCodeRegex
            )
            CustomTextField(
                givenValue = plateNumber,
                placeHolder = "Plate Number",
                onValueChange = { plateNumber = it.uppercase() },
                errorMessage = "Please enter correct plate",
                regex = String::plateRegex
            )
            CustomSelectionDialog(
                data = makesList.map { it.name },
                placeHolder = "Mark",
                onDataSelected = {
                    selectedMake = it
                },
                title = "Please select a mark"
            )
            CustomSelectionDialog(
                data = TempVariables.yearList,
                placeHolder = "Year",
                onDataSelected = {
                    selectedYear = it.toInt()
                },
                title = "Please select a year"
            )
            when (modelState) {
                is ScreenState.Success -> {
                    CustomSelectionDialog(
                        data = modelState.data.map { it.name },
                        placeHolder = "Modal",
                        onDataSelected = {
                            selectedModel = it
                        },
                        title = "Please select a modal"
                    )
                }

                else -> {
                    CustomTextField(
                        givenValue = "",
                        placeHolder = "Please first select mark and year",
                        onValueChange = {},
                        regex = String::passwordRegex,
                        enabled = false
                    )
                }
            }
            CustomTextField(
                givenValue = motorNumber,
                placeHolder = "Motor No",
                errorMessage = "Please enter correct motor number",
                onValueChange = { motorNumber = it },
                regex = String::motorNumberRegex
            )
            CustomTextField(
                givenValue = chassisNumber,
                placeHolder = "Chassis No",
                errorMessage = "Please enter correct chassis number",
                onValueChange = { chassisNumber = it.uppercase() },
                regex = String::chassisNumberRegex
            )
            AnimatedVisibility(visible = addedPolicy != null) {
                CustomText(
                    text = "Price: $price",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Row {
                CustomLargeIconButton(
                    isEnable = plateCode.plateCodeRegex() && selectedModel.isNotEmpty() && plateNumber.plateRegex() && motorNumber.motorNumberRegex() && chassisNumber.chassisNumberRegex(),
                    onClick = {
                        price =
                            ((Year.now().value - selectedYear) * customer.birthdate.calculateAge() * 25).toInt()
                        onTakeOfferClick(price, PolicyType.TRAFFIC.getTypeCode())
                    },
                    buttonText = R.string.take_price,
                    id = R.drawable.icon_payments,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 8.dp)
                )
                CustomLargeIconButton(
                    isEnable = addedPolicy != null,
                    onClick = { },
                    buttonText = R.string.new_policy,
                    id = R.drawable.icon_add_policy,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}