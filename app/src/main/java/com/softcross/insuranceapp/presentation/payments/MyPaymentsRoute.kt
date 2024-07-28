package com.softcross.insuranceapp.presentation.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.presentation.components.CustomAnnotatedText
import com.softcross.insuranceapp.presentation.components.CustomIconButton
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme


@Composable
fun MyPaymentsRoute(
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
                text = stringResource(id = R.string.my_policies),
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
            LoadingTextButton(
                isLoading = false,
                isEnable = true,
                onClick = { /*TODO*/ },
                buttonText = R.string.search
            )
            PoliciesResultContent()
        }
    }
}

@Composable
fun PoliciesResultContent() {
    val id = "12341352365"
    LazyColumn {
        items(10) {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
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
                        text = id,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 2.dp, top = 8.dp)
                    )
                    CustomAnnotatedText(
                        header = "Customer: ",
                        text = id,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Type: ",
                        text = id,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Payment Date: ",
                        text = id,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp, top = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Price: ",
                        text = id,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp, top = 2.dp)
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
                        onClick = { /*TODO*/ },
                        id = R.drawable.icon_trash
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MyPaymentsRoutePreview() {
    InsuranceAppTheme {
        MyPaymentsRoute()
    }
}