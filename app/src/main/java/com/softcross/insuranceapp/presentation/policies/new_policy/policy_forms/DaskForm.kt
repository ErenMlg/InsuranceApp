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
import androidx.core.text.isDigitsOnly
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.extensions.uavtRegex
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.presentation.components.CustomLargeIconButton
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField

@Composable
fun DaskPolicyForm(
    addedPolicy: Policy?,
    onTakeOfferClick: (Int, Int) -> Unit,
    onDaskCreate: (Dask) -> Unit
) {

    var uavt by remember { mutableStateOf("") }
    var apartmentMeter by remember { mutableStateOf("") }
    var apartmentFloor by remember { mutableStateOf("") }
    var apartmentAge by remember { mutableStateOf("") }
    var structType by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = addedPolicy) {
        if (addedPolicy != null) {
            onDaskCreate(
                Dask(
                    policyNo = addedPolicy.policyNo,
                    uavt = uavt,
                    apartmentMeter = apartmentMeter.toFloatOrNull() ?: 0f,
                    apartmentFloor = apartmentFloor.toIntOrNull() ?: 0,
                    apartmentAge = apartmentAge.toIntOrNull() ?: 0,
                    apartmentType = structType
                )
            )
        }
    }

    Column {
        CustomText(
            text = "Dask",
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
                givenValue = uavt,
                placeHolder = "UAVT",
                onValueChange = { if (it.length <= 10) uavt = it },
                errorMessage = "Please enter correct uavt",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                regex = String::uavtRegex
            )
            CustomTextField(
                givenValue = apartmentMeter,
                placeHolder = "Square meter",
                onValueChange = { apartmentMeter = it },
                errorMessage = "Please enter square meter",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                regex = String::isDigitsOnly
            )
            CustomTextField(
                givenValue = apartmentFloor,
                placeHolder = "Struct Floor",
                errorMessage = "Please enter correct floor",
                onValueChange = { apartmentFloor = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                regex = String::isDigitsOnly
            )
            CustomTextField(
                givenValue = apartmentAge,
                placeHolder = "Struct Age",
                errorMessage = "Please enter correct age",
                onValueChange = { apartmentAge = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                regex = String::isDigitsOnly
            )
            CustomSelectionDialog(
                data = listOf("Apartment", "Villa", "Mansion", "Residence", "Other"),
                placeHolder = "Struct Type",
                onDataSelected = { structType = it },
                title = "Please select a struct type"
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
                    isEnable = uavt.uavtRegex() && apartmentMeter.isDigitsOnly() && apartmentFloor.isDigitsOnly() && apartmentAge.isDigitsOnly() && structType.isNotEmpty(),
                    onClick = {
                        price =
                            (apartmentMeter.toInt() * 10) + (apartmentAge.toInt() * 25) + (apartmentFloor.toInt() * 10)
                        onTakeOfferClick(price, PolicyType.DASK.getTypeCode())
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