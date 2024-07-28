package com.softcross.insuranceapp.presentation.policies.new_policy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun NewPolicyRoute(
    modifier: Modifier = Modifier
) {
    var idNumber by remember { mutableStateOf("") }
    var nameSurname by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                text = stringResource(id = R.string.new_policy),
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Row {
                CustomTextField(
                    givenValue = idNumber,
                    placeHolder = stringResource(id = R.string.type),
                    onValueChange = { idNumber = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    regex = String::passwordRegex,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                )
                CustomTextField(
                    givenValue = nameSurname,
                    placeHolder = stringResource(id = R.string.customer),
                    onValueChange = { nameSurname = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    regex = String::passwordRegex,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                )
            }
            CustomText(
                text = "Traffic",
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            NewTrafficPolicyForm()
        }
    }
}

@Composable
fun NewTrafficPolicyForm(){
    Column {
        CustomTextField(
            givenValue = "",
            placeHolder = "Plate Code",
            onValueChange = {},
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = "",
            placeHolder = "Plate Number",
            onValueChange = {},
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = "",
            placeHolder = "Mark",
            onValueChange = {},
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = "",
            placeHolder = "Modal",
            onValueChange = {},
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = "",
            placeHolder = "Year",
            onValueChange = {},
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = "",
            placeHolder = "Motor No",
            onValueChange = {},
            regex = String::passwordRegex
        )
        CustomTextField(
            givenValue = "",
            placeHolder = "Chasis No",
            onValueChange = {},
            regex = String::passwordRegex
        )
        LoadingTextButton(
            isLoading = false,
            isEnable = true,
            onClick = { /*TODO*/ },
            buttonText = R.string.new_customer
        )
        LoadingTextButton(
            isLoading = false,
            isEnable = true,
            onClick = { /*TODO*/ },
            buttonText = R.string.new_customer
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun NewPolicyRoutePreview() {
    InsuranceAppTheme {
        NewPolicyRoute()
    }
}