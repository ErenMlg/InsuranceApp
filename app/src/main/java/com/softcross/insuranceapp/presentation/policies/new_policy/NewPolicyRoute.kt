package com.softcross.insuranceapp.presentation.policies.new_policy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.TempVariables
import com.softcross.insuranceapp.common.extensions.calculateAge
import com.softcross.insuranceapp.common.extensions.chassisNumberRegex
import com.softcross.insuranceapp.common.extensions.motorNumberRegex
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.common.extensions.plateCodeRegex
import com.softcross.insuranceapp.common.extensions.plateRegex
import com.softcross.insuranceapp.common.extensions.uavtRegex
import com.softcross.insuranceapp.domain.model.Traffic
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Kasko
import com.softcross.insuranceapp.domain.model.Models
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyStatus
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.presentation.components.CustomLargeIconButton
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import java.time.LocalDate
import java.time.Year


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewPolicyRoute(
    modifier: Modifier = Modifier,
    viewModel: NewPolicyViewModel = hiltViewModel()
) {
    val policyState = viewModel.policyState.value
    val pagerState = rememberPagerState { PolicyType.entries.size }
    var selectedType by remember { mutableStateOf("Unselected") }
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var addedPolicy by remember { mutableStateOf<Policy?>(null) }
    var snackbarMessage by remember { mutableStateOf("") }

    when (policyState) {
        is ScreenState.Error -> {
            snackbarMessage = policyState.message
        }

        ScreenState.Loading -> {
            snackbarMessage = ""
            addedPolicy = null
        }

        is ScreenState.Success -> {
            addedPolicy = policyState.data
        }
    }

    LaunchedEffect(key1 = selectedType, key2 = selectedCustomer) {
        if (selectedCustomer != null && selectedType != "Unselected") {
            when (selectedType) {
                "Traffic" -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.TRAFFIC.ordinal)
                }

                "Kasko" -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.KASKO.ordinal)
                }

                "Health" -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.HEALTH.ordinal)
                }

                "DASK" -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.DASK.ordinal)
                }
            }
        }
    }
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight()
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CustomText(
                text = stringResource(id = R.string.new_policy),
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Row {
                CustomSelectionDialog(
                    data = listOf("Traffic", "Kasko", "Health", "DASK"),
                    placeHolder = "Type",
                    onDataSelected = { selectedType = it },
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
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    PolicyType.UNSELECTED.ordinal -> UnselectedForm()

                    PolicyType.TRAFFIC.ordinal -> TrafficPolicyForm(
                        modelState = viewModel.modelState.value,
                        customer = TempVariables.customerList[0],
                        modelSearch = viewModel::getModels,
                        onTakeOfferClick = { price, code ->
                            val policy = Policy(
                                customerNo = TempVariables.customerList[0].id,
                                policyAgent = TempVariables.customerList[0].id,
                                policyPrim = price,
                                policyStatus = PolicyStatus.OFFER.getStatusCode(),
                                policyTypeCode = code,
                                policyEnterDate = LocalDate.now().toString()
                            )
                            viewModel.addPolicy(policy)
                        },
                        onCarCreate = { viewModel.addTraffic(traffic = it) },
                        onSetPolicyClick = {
                            val updatedPolicy = addedPolicy?.copy(
                                policyStatus = PolicyStatus.POLICY.getStatusCode(),
                                policyStartDate = LocalDate.now().toString(),
                                policyEndDate = LocalDate.now().plusYears(1).toString()
                            )
                        },
                        addedPolicy = addedPolicy
                    )

                    PolicyType.KASKO.ordinal -> KaskoPolicyForm(
                        modelState = viewModel.modelState.value,
                        modelSearch = viewModel::getModels,
                        onTakeOfferClick = { price, code ->
                            val policy = Policy(
                                customerNo = TempVariables.customerList[0].id,
                                policyAgent = TempVariables.customerList[0].id,
                                policyPrim = price,
                                policyStatus = PolicyStatus.OFFER.getStatusCode(),
                                policyTypeCode = code,
                                policyEnterDate = LocalDate.now().toString()
                            )
                            viewModel.addPolicy(policy)
                        },
                        onKaskoCreate = { viewModel.addKasko(kasko = it) },
                        onSetPolicyClick = {
                            val updatedPolicy = addedPolicy?.copy(
                                policyStatus = PolicyStatus.POLICY.getStatusCode(),
                                policyStartDate = LocalDate.now().toString(),
                                policyEndDate = LocalDate.now().plusYears(1).toString()
                            )
                        },
                        addedPolicy = addedPolicy
                    )

                    PolicyType.HEALTH.ordinal -> HealthPolicyForm(
                        addedPolicy = addedPolicy,
                        onTakeOfferClick = { price, code ->
                            val policy = Policy(
                                customerNo = TempVariables.customerList[0].id,
                                policyAgent = TempVariables.customerList[0].id,
                                policyPrim = price,
                                policyStatus = PolicyStatus.OFFER.getStatusCode(),
                                policyTypeCode = code,
                                policyEnterDate = LocalDate.now().toString()
                            )
                            viewModel.addPolicy(policy)
                        },
                        onHealthCreate = { viewModel.addHealth(health = it) }
                    )

                    PolicyType.DASK.ordinal -> DaskPolicyForm(
                        addedPolicy = addedPolicy,
                        onTakeOfferClick = { price, code ->
                            val policy = Policy(
                                customerNo = TempVariables.customerList[0].id,
                                policyAgent = TempVariables.customerList[0].id,
                                policyPrim = price,
                                policyStatus = PolicyStatus.OFFER.getStatusCode(),
                                policyTypeCode = code,
                                policyEnterDate = LocalDate.now().toString()
                            )
                            viewModel.addPolicy(policy)
                        },
                        onDaskCreate = { viewModel.addDask(dask = it) }
                    )
                }
            }
        }
        if (snackbarMessage.isNotEmpty()) {
            CustomSnackbar(
                errorMessage = snackbarMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun UnselectedForm() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_error),
            contentDescription = "",
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 16.dp)
        )
        CustomText(
            text = "Please select a policy type and customer",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            line = Int.MAX_VALUE,
            modifier = Modifier
                .fillMaxWidth(0.5f)
        )
    }
}

@Composable
fun TrafficPolicyForm(
    modelState: ScreenState<List<Models>>,
    customer: Customer,
    modelSearch: (Int, Int) -> Unit,
    onTakeOfferClick: (Int, Int) -> Unit,
    onSetPolicyClick: (Policy) -> Unit,
    onCarCreate: (Traffic) -> Unit,
    addedPolicy: Policy?,
) {
    val makesList = TempVariables.makeList
    var plateCode by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var motorNumber by remember { mutableStateOf("") }
    var chassisNumber by remember { mutableStateOf("") }
    var selectedMake by remember { mutableStateOf("") }
    var selectedYear by remember { mutableIntStateOf(0) }
    var selectedModel by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(0) }
    val selectedMakeID by remember {
        derivedStateOf { makesList.find { makes -> makes.name == selectedMake }?.id ?: 0 }
    }

    LaunchedEffect(key1 = selectedMakeID, key2 = selectedYear) {
        selectedModel = ""
        if (selectedMakeID != 0 && selectedYear != 0) {
            modelSearch(selectedMakeID, selectedYear)
        }
    }

    LaunchedEffect(key1 = addedPolicy) {
        if (addedPolicy != null) {
            println("Selamın Aleyküm")
            onCarCreate(
                Traffic(
                    policyNo = addedPolicy.policyNo,
                    plate = plateCode.toIntOrNull() ?: 0,
                    plateCode = plateNumber,
                    make = selectedMake,
                    model = selectedModel,
                    year = selectedYear,
                    chassisNo = chassisNumber,
                    engineNo = motorNumber
                )
            )
        }
    }

    Column {
        CustomText(
            text = "Traffic",
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
                givenValue = plateCode,
                placeHolder = "Plate Code",
                onValueChange = { if (it.length <= 2) plateCode = it },
                errorMessage = "Plate code must be between 1 and 81",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                regex = String::plateCodeRegex
            )
            CustomTextField(
                givenValue = plateNumber,
                placeHolder = "Plate Number",
                onValueChange = { plateNumber = it.uppercase() },
                errorMessage = "Please enter correct plate",
                regex = String::plateRegex
            )
            CustomSelectionDialog(
                data = makesList.map { it.name },
                placeHolder = "Mark",
                onDataSelected = {
                    selectedMake = it
                },
                title = "Please select a mark"
            )
            CustomSelectionDialog(
                data = TempVariables.yearList,
                placeHolder = "Year",
                onDataSelected = {
                    selectedYear = it.toInt()
                },
                title = "Please select a year"
            )
            when (modelState) {
                is ScreenState.Success -> {
                    CustomSelectionDialog(
                        data = modelState.data.map { it.name },
                        placeHolder = "Modal",
                        onDataSelected = {
                            selectedModel = it
                        },
                        title = "Please select a modal"
                    )
                }

                else -> {
                    CustomTextField(
                        givenValue = "",
                        placeHolder = "Please first select mark and year",
                        onValueChange = {},
                        regex = String::passwordRegex,
                        enabled = false
                    )
                }
            }
            CustomTextField(
                givenValue = motorNumber,
                placeHolder = "Motor No",
                errorMessage = "Please enter correct motor number",
                onValueChange = { motorNumber = it },
                regex = String::motorNumberRegex
            )
            CustomTextField(
                givenValue = chassisNumber,
                placeHolder = "Chassis No",
                errorMessage = "Please enter correct chassis number",
                onValueChange = { chassisNumber = it.uppercase() },
                regex = String::chassisNumberRegex
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
                    isEnable = plateCode.plateCodeRegex() && selectedModel.isNotEmpty() && plateNumber.plateRegex() && motorNumber.motorNumberRegex() && chassisNumber.chassisNumberRegex(),
                    onClick = {
                        price =
                            ((Year.now().value - selectedYear) * customer.birthdate.calculateAge() * 25).toInt()
                        onTakeOfferClick(price, PolicyType.TRAFFIC.getTypeCode())
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

@Composable
fun KaskoPolicyForm(
    modelState: ScreenState<List<Models>>,
    modelSearch: (Int, Int) -> Unit,
    onTakeOfferClick: (Int, Int) -> Unit,
    onSetPolicyClick: (Policy) -> Unit,
    onKaskoCreate: (Kasko) -> Unit,
    addedPolicy: Policy?,
) {
    val makesList = TempVariables.makeList
    var plateCode by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var motorNumber by remember { mutableStateOf("") }
    var chassisNumber by remember { mutableStateOf("") }
    var selectedMake by remember { mutableStateOf("") }
    var selectedYear by remember { mutableIntStateOf(0) }
    var selectedModel by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(0) }
    val selectedMakeID by remember {
        derivedStateOf { makesList.find { makes -> makes.name == selectedMake }?.id ?: 0 }
    }

    LaunchedEffect(key1 = selectedMakeID, key2 = selectedYear) {
        selectedModel = ""
        if (selectedMakeID != 0 && selectedYear != 0) {
            modelSearch(selectedMakeID, selectedYear)
        }
    }

    LaunchedEffect(key1 = addedPolicy) {
        if (addedPolicy != null) {
            onKaskoCreate(
                Kasko(
                    policyNo = addedPolicy.policyNo,
                    plate = plateCode.toIntOrNull() ?: 0,
                    plateCode = plateNumber,
                    make = selectedMake,
                    model = selectedModel,
                    year = selectedYear,
                    chassisNo = chassisNumber,
                    engineNo = motorNumber
                )
            )
        }
    }

    Column {
        CustomText(
            text = "Kasko",
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
                givenValue = plateCode,
                placeHolder = "Plate Code",
                onValueChange = { if (it.length <= 2) plateCode = it },
                errorMessage = "Plate code must be between 1 and 81",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                regex = String::plateCodeRegex
            )
            CustomTextField(
                givenValue = plateNumber,
                placeHolder = "Plate Number",
                onValueChange = { plateNumber = it.uppercase() },
                errorMessage = "Please enter correct plate",
                regex = String::plateRegex
            )
            CustomSelectionDialog(
                data = makesList.map { it.name },
                placeHolder = "Mark",
                onDataSelected = {
                    selectedMake = it
                },
                title = "Please select a mark"
            )
            CustomSelectionDialog(
                data = TempVariables.yearList,
                placeHolder = "Year",
                onDataSelected = {
                    selectedYear = it.toInt()
                },
                title = "Please select a year"
            )
            when (modelState) {
                is ScreenState.Success -> {
                    CustomSelectionDialog(
                        data = modelState.data.map { it.name },
                        placeHolder = "Modal",
                        onDataSelected = {
                            selectedModel = it
                        },
                        title = "Please select a modal"
                    )
                }

                else -> {
                    CustomTextField(
                        givenValue = "",
                        placeHolder = "Please first select mark and year",
                        onValueChange = {},
                        regex = String::passwordRegex,
                        enabled = false
                    )
                }
            }
            CustomTextField(
                givenValue = motorNumber,
                placeHolder = "Motor No",
                errorMessage = "Please enter correct motor number",
                onValueChange = { motorNumber = it },
                regex = String::motorNumberRegex
            )
            CustomTextField(
                givenValue = chassisNumber,
                placeHolder = "Chassis No",
                errorMessage = "Please enter correct chassis number",
                onValueChange = { chassisNumber = it.uppercase() },
                regex = String::chassisNumberRegex
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
                    isEnable = plateCode.plateCodeRegex() && plateNumber.plateRegex() && motorNumber.motorNumberRegex() && chassisNumber.chassisNumberRegex() && selectedModel.isNotEmpty(),
                    onClick = {
                        price =
                            ((Year.now().value - selectedYear) * 100)
                        onTakeOfferClick(price, PolicyType.KASKO.getTypeCode())
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

@Composable
fun HealthPolicyForm(
    addedPolicy: Policy?,
    onTakeOfferClick: (Int, Int) -> Unit,
    onHealthCreate: (Health) -> Unit
) {

    var smoke by remember { mutableStateOf(false) }
    var alcohol by remember { mutableStateOf(false) }
    var drugs by remember { mutableStateOf(false) }
    var sport by remember { mutableStateOf(false) }
    var surgery by remember { mutableStateOf(false) }
    var allergy by remember { mutableStateOf(false) }
    var price by remember { mutableIntStateOf(500) }
    var showedPrice by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = addedPolicy) {
        if (addedPolicy != null) {
            onHealthCreate(
                Health(
                    policyNo = addedPolicy.policyNo,
                    smoke = smoke,
                    alcohol = alcohol,
                    drugs = drugs,
                    sport = sport,
                    surgery = surgery,
                    allergy = allergy
                )
            )
        }
    }

    Column {
        CustomText(
            text = "Health",
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
            SwipeRow("Smoke", smoke) { smoke = it; if (it) price += 500 else price -= 500 }
            HorizontalDivider()
            SwipeRow("Alcohol", alcohol) { alcohol = it; if (it) price += 500 else price -= 500 }
            HorizontalDivider()
            SwipeRow("Drugs", drugs) { drugs = it; if (it) price += 1000 else price -= 1000 }
            HorizontalDivider()
            SwipeRow("Sport", sport) { sport = it; if (it) price -= 1000 else price += 1000 }
            HorizontalDivider()
            SwipeRow("Surgery", surgery) { surgery = it; if (it) price += 250 else price -= 250 }
            HorizontalDivider()
            SwipeRow("Allergy", allergy) { allergy = it; if (it) price += 100 else price -= 100 }
            AnimatedVisibility(visible = addedPolicy != null) {
                CustomText(
                    text = "Price: $showedPrice",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Row {
                CustomLargeIconButton(
                    isEnable = true,
                    onClick = {
                        showedPrice = price
                        onTakeOfferClick(price, PolicyType.HEALTH.getTypeCode())
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

@Composable
fun SwipeRow(text: String, data: Boolean, onChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomText(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Switch(
            checked = data,
            onCheckedChange = onChecked,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}
