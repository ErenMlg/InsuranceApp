package com.softcross.insuranceapp.presentation.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.AllCustomers
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.domain.model.Payment
import com.softcross.insuranceapp.presentation.components.CustomAnnotatedText
import com.softcross.insuranceapp.presentation.components.CustomIconButton
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme


@Composable
fun MyPaymentsRoute(
    modifier: Modifier = Modifier,
    viewModel: MyPaymentsViewModel = hiltViewModel()
) {
    val paymentState = viewModel.paymentState.value

    Column(
        modifier = modifier
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
                text = stringResource(id = R.string.my_payments),
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            if (paymentState is ScreenState.Success) {
                PaymentsResultContent(
                    paymentList = paymentState.data,
                    onDelete = viewModel::deletePayment
                )
            } else {
                CustomText(
                    text = "Loading...",
                    fontFamilyID = R.font.poppins_regular,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PaymentsResultContent(
    paymentList: List<Payment>,
    onDelete: (String) -> Unit
) {
    if (paymentList.isEmpty()) {
        CustomText(
            text = "No payment found",
            fontFamilyID = R.font.poppins_regular,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
    } else {
        LazyColumn {
            items(paymentList.size, key = { paymentList[it].policyNo }) { index ->
                val payment = paymentList[index]
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomAnnotatedText(
                            header = "Police No: ",
                            text = payment.policyNo,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        CustomAnnotatedText(
                            header = "Payment Date: ",
                            text = payment.paymentDate,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        CustomAnnotatedText(
                            header = "Price: ",
                            text = payment.paymentAmount.toString() + " ₺",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomIconButton(
                            iconModifier = Modifier.size(18.dp),
                            modifier = Modifier,
                            onClick = { onDelete(payment.policyNo) },
                            id = R.drawable.icon_trash
                        )
                    }
                }
            }
        }
    }
}
