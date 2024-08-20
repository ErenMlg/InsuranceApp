package com.softcross.insuranceapp.presentation.payments.new_payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.CreditCardDateVisualTransformation
import com.softcross.insuranceapp.common.CreditCardNumberVisualTransformation
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.extensions.creditCardDateRegex
import com.softcross.insuranceapp.common.extensions.creditCardNumberRegex
import com.softcross.insuranceapp.common.extensions.cvcRegex
import com.softcross.insuranceapp.common.extensions.nameSurnameRegex
import com.softcross.insuranceapp.common.extensions.nameSurnameRegexWithSpace
import com.softcross.insuranceapp.domain.model.Payment
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.presentation.components.CustomAnnotatedText
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingIconButton
import kotlinx.coroutines.delay
import java.time.LocalDate

@Composable
fun NewPaymentRoute(
    modifier: Modifier = Modifier,
    viewModel: NewPaymentViewModel = hiltViewModel(),
    onHome: () -> Unit
) {

    val policyState = viewModel.policyState.value
    val paymentState = viewModel.paymentState.value
    var isError by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = snackbarMessage) {
        if (snackbarMessage.isNotEmpty() && !isError) {
            delay(2000)
            onHome()
        }
    }

    when (paymentState) {
        is ScreenState.Loading -> {
            snackbarMessage = ""
        }

        is ScreenState.Success -> {
            snackbarMessage = "Payment is successful"
            isError = false
        }

        is ScreenState.Error -> {
            snackbarMessage = paymentState.message
            isError = true
        }
    }

    Box {
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
            if (policyState is ScreenState.Success) {
                PaymentForm(policy = policyState.data, onPay = viewModel::makePayment, onUpdatePolicy = viewModel::updatePolicy)
            }

            if (policyState is ScreenState.Loading) {
                CircularProgressIndicator()
            }

            if (policyState is ScreenState.Error) {
                CustomText(
                    text = policyState.message,
                    fontFamilyID = R.font.poppins_regular,
                    fontSize = 14.sp,
                    line = Int.MAX_VALUE,
                    modifier = Modifier.padding(16.dp)
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
fun PaymentForm(
    policy: Policy,
    onPay: (Payment) -> Unit,
    onUpdatePolicy : (Policy) -> Unit
) {

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
            givenValue = policy.policyNo,
            placeHolder = stringResource(id = R.string.field_policy_no),
            onValueChange = { },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = String::isNotEmpty,
            errorMessage = stringResource(id = R.string.field_policy_no),
            enabled = false
        )
        CustomTextField(
            givenValue = creditCardNumber,
            placeHolder = stringResource(id = R.string.field_credit_card_number),
            onValueChange = { if (it.length <= 16) creditCardNumber = it },
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
            onValueChange = { if (it.length <= 4) cardDate = it },
            regex = String::creditCardDateRegex,
            errorMessage = stringResource(id = R.string.valid_card_date)
        )
        CustomTextField(
            givenValue = cardName,
            placeHolder = stringResource(id = R.string.field_card_name),
            onValueChange = { cardName = it },
            regex = String::nameSurnameRegexWithSpace,
            errorMessage = stringResource(id = R.string.valid_name)
        )
        CustomTextField(
            givenValue = cardCVC,
            placeHolder = stringResource(id = R.string.field_card_cvc),
            onValueChange = { if (it.length <= 3) cardCVC = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = String::cvcRegex,
            errorMessage = stringResource(id = R.string.valid_card_cvc)
        )
        CustomAnnotatedText(
            header = "Price: ",
            text = "${policy.policyPrim} ₺",
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        LoadingIconButton(
            isLoading = false,
            isEnable = true,
            onClick = {
                onPay(
                    Payment(
                        policyNo = policy.policyNo,
                        creditCardNumber = creditCardNumber,
                        cardDate = cardDate,
                        cardName = cardName,
                        cardCVC = cardCVC,
                        paymentDate = LocalDate.now().toString(),
                        paymentAmount = policy.policyPrim,
                        )
                )
                onUpdatePolicy(
                    Policy(
                        policyNo = policy.policyNo,
                        customerNo = policy.customerNo,
                        policyAgent = policy.policyAgent,
                        policyPrim = policy.policyPrim,
                        policyStatus = 'P',
                        policyTypeCode = policy.policyTypeCode,
                        policyEnterDate = policy.policyEnterDate,
                        policyStartDate = LocalDate.now().toString(),
                        policyEndDate = LocalDate.now().plusYears(1).toString()
                    )
                )
            },
            buttonText = R.string.pay,
            id = R.drawable.icon_payments
        )
    }
}

