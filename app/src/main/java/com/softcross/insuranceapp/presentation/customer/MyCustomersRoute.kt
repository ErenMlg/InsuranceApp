package com.softcross.insuranceapp.presentation.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun MyCustomersRoute(
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
                text = stringResource(id = R.string.my_customers),
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            CustomText(
                text = stringResource(id = R.string.customers_info_text),
                fontSize = 14.sp,
                line = 2,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
            Row {
                CustomTextField(
                    givenValue = idNumber,
                    placeHolder = stringResource(id = R.string.field_just_id),
                    onValueChange = { idNumber = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    regex = String::passwordRegex,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                )
                CustomTextField(
                    givenValue = nameSurname,
                    placeHolder = stringResource(id = R.string.field_just_name),
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
            CustomerResultContent()
        }
    }
}

@Composable
fun CustomerResultContent() {
    LazyColumn {
        items(10) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .shadow(2.dp, RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Selam")
                    Text(text = "Selam")
                    Text(text = "Selam")
                    Text(text = "Selam")
                }
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "",
                    )
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "",
                    )
                }
            }
            LoadingTextButton(
                isLoading = false,
                isEnable = true,
                onClick = { /*TODO*/ },
                buttonText = R.string.show_policies
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyCustomersRoutePreview() {
    InsuranceAppTheme {
        MyCustomersRoute()
    }
}