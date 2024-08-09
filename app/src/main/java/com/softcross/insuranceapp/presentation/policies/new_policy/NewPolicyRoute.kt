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
import com.softcross.insuranceapp.domain.model.getPolicyByName
import com.softcross.insuranceapp.presentation.components.CustomLargeIconButton
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.components.CustomTextField
import com.softcross.insuranceapp.presentation.components.LoadingTextButton
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.DaskPolicyForm
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.HealthPolicyForm
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.KaskoPolicyForm
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.TrafficPolicyForm
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
    var selectedType by remember { mutableStateOf(PolicyType.UNSELECTED) }
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
        if (selectedCustomer != null && selectedType != PolicyType.UNSELECTED) {
            when (selectedType) {
                PolicyType.TRAFFIC -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.TRAFFIC.ordinal)
                }

                PolicyType.KASKO -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.KASKO.ordinal)
                }

                PolicyType.HEALTH -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.HEALTH.ordinal)
                }

                PolicyType.DASK -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.DASK.ordinal)
                }

                PolicyType.UNSELECTED -> {
                    addedPolicy = null
                    pagerState.animateScrollToPage(PolicyType.UNSELECTED.ordinal)
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


