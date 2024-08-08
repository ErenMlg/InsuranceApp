package com.softcross.insuranceapp.presentation.policies

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
import com.softcross.insuranceapp.common.TempVariables
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.domain.model.getPolicyByID
import com.softcross.insuranceapp.domain.model.getPolicyStatusByCode
import com.softcross.insuranceapp.presentation.components.CustomAnnotatedText
import com.softcross.insuranceapp.presentation.components.CustomIconButton
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme


@Composable
fun MyPoliciesRoute(
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
            PoliciesResultContent(
                listOf(
                    Policy(
                        policyNo = "penatibus",
                        customerNo = "errem",
                        policyAgent = "principes",
                        policyPrim = 1739,
                        policyStatus = 'T',
                        policyTypeCode = 2469,
                        policyEnterDate = "eam",
                        policyStartDate = null,
                        policyEndDate = null
                    ),
                    Policy(
                        policyNo = "penatibus",
                        customerNo = "errem",
                        policyAgent = "principes",
                        policyPrim = 1739,
                        policyStatus = 'P',
                        policyTypeCode = 2469,
                        policyEnterDate = "eam",
                        policyStartDate = null,
                        policyEndDate = "2024-12-12"
                    ),
                )
            )
        }
    }
}

@Composable
fun PoliciesResultContent(
    policyList: List<Policy>
) {
    val id = "12341352365"
    LazyColumn(
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        items(policyList.size) { index ->
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
                        text = policyList[index].policyNo,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 2.dp, top = 8.dp)
                    )
                    CustomAnnotatedText(
                        header = "Customer: ",
                        text = policyList[index].customerNo,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Type: ",
                        text = getPolicyByID(policyList[index].policyTypeCode),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Status: ",
                        text = getPolicyStatusByCode(policyList[index].policyStatus),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Record Date: ",
                        text = policyList[index].policyEnterDate,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    if (policyList[index].policyStatus == 'P') {
                        CustomAnnotatedText(
                            header = "Start Date: ",
                            text = policyList[index].policyStartDate ?: "Unkown",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        CustomAnnotatedText(
                            header = "End Date: ",
                            text = policyList[index].policyEndDate ?: "Unkown",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    CustomAnnotatedText(
                        header = "Price: ",
                        text = policyList[index].policyPrim.toString().plus(" ₺"),
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
                        onClick = { /*TODO*/ },
                        id = R.drawable.icon_edit
                    )
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
fun MyPoliciesRoutePreview() {
    InsuranceAppTheme {
        MyPoliciesRoute()
    }
}