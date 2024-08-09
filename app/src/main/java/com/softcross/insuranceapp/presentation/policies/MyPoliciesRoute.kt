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
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.TempVariables
import com.softcross.insuranceapp.common.extensions.dateTimeToFormattedDate
import com.softcross.insuranceapp.common.extensions.formatBirthday
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.domain.model.getPolicyByID
import com.softcross.insuranceapp.domain.model.getPolicyByName
import com.softcross.insuranceapp.domain.model.getPolicyStatusByCode
import com.softcross.insuranceapp.presentation.components.CustomAnnotatedText
import com.softcross.insuranceapp.presentation.components.CustomIconButton
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme


@Composable
fun MyPoliciesRoute(
    modifier: Modifier = Modifier,
    viewModel: MyPoliciesViewModel = hiltViewModel()
) {
    var nameSurname by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(PolicyType.UNSELECTED) }
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    val state = viewModel.policyState.value
    var policyList by remember { mutableStateOf<List<Policy>>(emptyList()) }

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
                CustomSelectionDialog(
                    data = listOf("Traffic", "Kasko", "Health", "DASK"),
                    placeHolder = "Type",
                    onDataSelected = { selectedType = getPolicyByName(it) },
                    title = "Please select a policy type",
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                )
                CustomSelectionDialog(
                    data = TempVariables.customerList.map { it.name + " " + it.surname },
                    placeHolder = "Customer",
                    onDataSelected = {
                        selectedCustomer = TempVariables.findCustomerByName(it)
                    },
                    title = "Please select a customer",
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                )
            }
            LoadingTextButton(
                isLoading = false,
                isEnable = selectedType != PolicyType.UNSELECTED || selectedCustomer != null,
                onClick = {
                    if (selectedCustomer != null) {
                        if (selectedType != PolicyType.UNSELECTED) {
                            viewModel.searchPolicy(selectedCustomer!!.id, selectedType)
                        }else{
                            viewModel.searchPolicy(selectedCustomer!!.id)
                        }
                    }else{
                        if (selectedType != PolicyType.UNSELECTED) {
                            viewModel.searchPolicy("0", selectedType)
                        }
                    }
                },
                buttonText = R.string.search
            )
            when (state) {
                is ScreenState.Loading -> {

                }

                is ScreenState.Success -> {
                    policyList = state.data
                    PoliciesResultContent(policyList)
                }

                else -> {}
            }
        }
    }
}

@Composable
fun PoliciesResultContent(
    policyList: List<Policy>
) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        items(policyList.size) { index ->
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
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).weight(0.8f),
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
                        text = policyList[index].policyEnterDate.dateTimeToFormattedDate(),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    if (policyList[index].policyStatus == 'P') {
                        CustomAnnotatedText(
                            header = "Start Date: ",
                            text = (policyList[index].policyStartDate ?: "Unkown").dateTimeToFormattedDate(),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        CustomAnnotatedText(
                            header = "End Date: ",
                            text = (policyList[index].policyEndDate ?: "Unkown").dateTimeToFormattedDate(),
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
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).weight(0.2f),
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
                        onClick = {},
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