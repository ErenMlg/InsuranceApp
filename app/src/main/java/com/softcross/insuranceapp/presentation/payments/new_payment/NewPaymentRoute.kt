package com.softcross.insuranceapp.presentation.payments.new_payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.CreditCardDateVisualTransformation
import com.softcross.insuranceapp.common.CreditCardNumberVisualTransformation
import com.softcross.insuranceapp.common.PhoneNumberVisualTransformation
import com.softcross.insuranceapp.common.extensions.creditCardDateRegex
import com.softcross.insuranceapp.common.extensions.creditCardNumberRegex
import com.softcross.insuranceapp.common.extensions.cvcRegex
import com.softcross.insuranceapp.common.extensions.emailRegex
import com.softcross.insuranceapp.common.extensions.idRegex
import com.softcross.insuranceapp.common.extensions.nameSurnameRegex
import com.softcross.insuranceapp.common.extensions.phoneRegex
import com.softcross.insuranceapp.presentation.components.CustomAnnotatedText
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingIconButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun NewPaymentRoute(
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
            text = stringResource(id = R.string.payment),
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
        PaymentForm()
    }
}

@Composable
fun PaymentForm(){

    var creditCardNumber by remember { mutableStateOf("") }
    var cardDate by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }
    var cardCVC by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(0.9f)
            .verticalScroll(rememberScrollState())
    ) {
        CustomTextField(
            givenValue = "",
            placeHolder = stringResource(id = R.string.field_policy_no),
            onValueChange = {  },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = String::idRegex,
            errorMessage = stringResource(id = R.string.field_policy_no),
            enabled = false
        )
        CustomTextField(
            givenValue = creditCardNumber,
            placeHolder = stringResource(id = R.string.field_credit_card_number),
            onValueChange = { creditCardNumber = it },
            visualTransformation = CreditCardNumberVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = String::creditCardNumberRegex,
            errorMessage = stringResource(id = R.string.valid_credit_card)
        )
        CustomTextField(
            givenValue = cardDate,
            visualTransformation = CreditCardDateVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeHolder = stringResource(id = R.string.field_card_date),
            onValueChange = { cardDate = it },
            regex = String::creditCardDateRegex,
            errorMessage = stringResource(id = R.string.valid_card_date)
        )
        CustomTextField(
            givenValue = cardName,
            placeHolder = stringResource(id = R.string.field_card_name),
            onValueChange = { cardName = it },
            regex = String::nameSurnameRegex,
            errorMessage = stringResource(id = R.string.valid_name)
        )
        CustomTextField(
            givenValue = cardCVC,
            placeHolder = stringResource(id = R.string.field_card_cvc),
            onValueChange = { cardCVC = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = String::cvcRegex,
            errorMessage = stringResource(id = R.string.valid_card_cvc)
        )
        CustomAnnotatedText(
            header = "Price: ",
            text = "1000 TL",
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        LoadingIconButton(
            isLoading = false,
            isEnable = true,
            onClick = {
            },
            buttonText = R.string.pay,
            id = R.drawable.icon_payments
        )
    }
}

@Preview
@Composable
fun NewPaymentRoutePreview() {
    InsuranceAppTheme {
        NewPaymentRoute()
    }
}

