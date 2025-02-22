package com.softcross.insuranceapp.presentation.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.UserCustomers
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.presentation.components.CustomAnnotatedText
import com.softcross.insuranceapp.presentation.components.CustomIconButton
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.LoadingIconButton
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun MyCustomersRoute(
    modifier: Modifier = Modifier,
    viewModel: MyCustomersViewModel = hiltViewModel(),
    onEditClick: (String) -> Unit
) {
    var idNumber by remember { mutableStateOf("") }
    var nameSurname by remember { mutableStateOf("") }
    var customerList by remember { mutableStateOf(UserCustomers.getCustomerList()) }
    var snackbarMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState = viewModel.customerState.value

    if (uiState is ScreenState.Error) {
        snackbarMessage = uiState.message
        isError = true
    }

    if (uiState is ScreenState.Success) {
        snackbarMessage = stringResource(id = R.string.success_delete_customer)
        customerList = uiState.data
        isError = false
    }

    Box {
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
                        regex = String::isNotEmpty,
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )
                    CustomTextField(
                        givenValue = nameSurname,
                        placeHolder = stringResource(id = R.string.field_just_name),
                        onValueChange = { nameSurname = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        regex = String::isNotEmpty,
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    )
                }
                LoadingIconButton(
                    isEnable = idNumber.isNotEmpty() || nameSurname.isNotEmpty(),
                    onClick = {
                        keyboardController?.hide()
                        customerList = UserCustomers.getCustomerList().filter {
                            if (idNumber.isNotEmpty() && nameSurname.isNotEmpty()) {
                                it.id.contains(idNumber) && (it.name + " " + it.surname).contains(
                                    nameSurname,
                                    ignoreCase = true
                                )
                            } else if (idNumber.isNotEmpty()) {
                                it.id.contains(idNumber)
                            } else {
                                (it.name + " " + it.surname).contains(
                                    nameSurname,
                                    ignoreCase = true
                                )
                            }
                        }
                    },
                    buttonText = R.string.search,
                    id = R.drawable.icon_search
                )
                CustomerResultContent(
                    customers = customerList,
                    onDelete = {
                        idNumber = ""
                        nameSurname = ""
                        viewModel.deleteCustomer(it)
                    },
                    onEditClick = onEditClick
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
fun CustomerResultContent(
    customers: List<Customer>, onDelete: (String) -> Unit,
    onEditClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(customers.size, key = { customers[it].id }) { index ->
            val customer = customers[index]
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
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .weight(0.8f),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    CustomAnnotatedText(
                        header = "ID: ",
                        text = customer.id,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 2.dp, top = 8.dp)
                    )
                    CustomAnnotatedText(
                        header = "Name: ",
                        text = customer.name,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Surname: ",
                        text = customer.surname,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    CustomAnnotatedText(
                        header = "Address: ",
                        text = customer.city + " / " + customer.district,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp, top = 2.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .weight(0.2f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomIconButton(
                        iconModifier = Modifier.size(18.dp),
                        modifier = Modifier,
                        onClick = { onEditClick(customer.id) },
                        id = R.drawable.icon_edit
                    )
                    CustomIconButton(
                        iconModifier = Modifier.size(18.dp),
                        modifier = Modifier,
                        onClick = {
                            onDelete(customer.id)
                        },
                        id = R.drawable.icon_trash
                    )
                }
            }
        }
    }
}