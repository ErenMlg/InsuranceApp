package com.softcross.insuranceapp.presentation.policies.new_policy

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.HierarchicalFocusCoordinator
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.AllCustomers
import com.softcross.insuranceapp.common.CurrentUser
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.UserCustomers
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyStatus
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.domain.model.getPolicyByName
import com.softcross.insuranceapp.presentation.components.CustomSelectionDialog
import com.softcross.insuranceapp.presentation.components.CustomSnackbar
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.DaskPolicyForm
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.HealthPolicyForm
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.KaskoPolicyForm
import com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms.TrafficPolicyForm
import kotlinx.coroutines.delay
import java.time.LocalDate


@OptIn(ExperimentalFoundationApi::class, ExperimentalWearFoundationApi::class)
@Composable
fun NewPolicyRoute(
    modifier: Modifier = Modifier,
    viewModel: NewPolicyViewModel = hiltViewModel(),
    onPay: (String) -> Unit
) {
    val policyState = viewModel.policyState.value
    val pagerState = rememberPagerState { PolicyType.entries.size }
    var selectedType by remember { mutableStateOf(PolicyType.UNSELECTED) }
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var addedPolicy by remember { mutableStateOf<Policy?>(null) }
    var snackbarMessage by remember { mutableStateOf("") }
    val onTakeOffer: (Int, Int) -> Unit = { price: Int, code: Int ->
        selectedCustomer?.let { customer ->
            viewModel.addPolicy(
                Policy(
                    customerNo = customer.id,
                    policyAgent = CurrentUser.getCurrentUserID(),
                    policyPrim = price,
                    policyStatus = PolicyStatus.OFFER.getStatusCode(),
                    policyTypeCode = code,
                    policyEnterDate = LocalDate.now().toString()
                )
            )
        }
    }
    val onSetPolicyClick: () -> Unit = {
        onPay(addedPolicy?.policyNo ?: "")
    }

    when (policyState) {
        is ScreenState.Error -> {
            snackbarMessage = policyState.message
        }

        ScreenState.Loading -> {
            snackbarMessage = "";
            addedPolicy = null
        }

        is ScreenState.Success -> {
            addedPolicy = policyState.data
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            delay(100)
            selectedType = PolicyType.entries.toTypedArray()[page]
        }
    }

    LaunchedEffect(key1 = selectedType, key2 = selectedCustomer) {
        if (selectedCustomer != null && selectedType != PolicyType.UNSELECTED) {
            when (selectedType) {
                PolicyType.TRAFFIC -> {
                    addedPolicy = null
                    pagerState.scrollToPage(PolicyType.TRAFFIC.ordinal)
                }

                PolicyType.KASKO -> {
                    addedPolicy = null
                    pagerState.scrollToPage(PolicyType.KASKO.ordinal)
                }

                PolicyType.HEALTH -> {
                    addedPolicy = null
                    pagerState.scrollToPage(PolicyType.HEALTH.ordinal)
                }

                PolicyType.DASK -> {
                    addedPolicy = null
                    pagerState.scrollToPage(PolicyType.DASK.ordinal)
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
                    data = AllCustomers.getCustomerList().map { it.name + " " + it.surname },
                    placeHolder = "Customer",
                    onDataSelected = {
                        selectedCustomer = AllCustomers.getCustomerByName(it)
                    },
                    title = "Please select a customer",
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                )
            }

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
            ) { page ->
                ClippedBox(pagerState = pagerState) {
                    HierarchicalFocusCoordinator(requiresFocus = { page == pagerState.currentPage }) {
                        when (page) {
                            PolicyType.UNSELECTED.ordinal -> {
                                UnselectedForm()
                            }

                            PolicyType.TRAFFIC.ordinal -> TrafficPolicyForm(
                                modelState = viewModel.modelState.value,
                                customer = selectedCustomer ?: UserCustomers.getCustomerByIndex(0),
                                modelSearch = viewModel::getModels,
                                onTakeOfferClick = onTakeOffer,
                                onCarCreate = { viewModel.addTraffic(traffic = it) },
                                onSetPolicyClick = onSetPolicyClick,
                                addedPolicy = addedPolicy
                            )

                            PolicyType.KASKO.ordinal -> KaskoPolicyForm(
                                modelState = viewModel.modelState.value,
                                modelSearch = viewModel::getModels,
                                onTakeOfferClick = onTakeOffer,
                                onKaskoCreate = { viewModel.addKasko(kasko = it) },
                                onSetPolicyClick = onSetPolicyClick,
                                addedPolicy = addedPolicy
                            )

                            PolicyType.HEALTH.ordinal -> HealthPolicyForm(
                                addedPolicy = addedPolicy,
                                onTakeOfferClick = onTakeOffer,
                                onHealthCreate = { viewModel.addHealth(health = it) },
                                onSetPolicyClick = onSetPolicyClick,
                            )

                            PolicyType.DASK.ordinal -> DaskPolicyForm(
                                addedPolicy = addedPolicy,
                                onTakeOfferClick = onTakeOffer,
                                onSetPolicyClick = onSetPolicyClick,
                                onDaskCreate = { viewModel.addDask(dask = it) }
                            )
                        }
                    }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ClippedBox(pagerState: PagerState, content: @Composable () -> Unit) {
    val shape = rememberClipWhenScrolling(pagerState)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .optionalClip(shape),
    ) {
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun rememberClipWhenScrolling(state: PagerState): State<RoundedCornerShape?> {
    val shape = if (LocalConfiguration.current.isScreenRound) CircleShape else null
    return remember(state) {
        derivedStateOf {
            if (shape != null && state.currentPageOffsetFraction != 0f) {
                shape
            } else {
                null
            }
        }
    }
}

private fun Modifier.optionalClip(shapeState: State<RoundedCornerShape?>): Modifier {
    val shape = shapeState.value

    return if (shape != null) {
        clip(shape)
    } else {
        this
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
            text = stringResource(id = R.string.please_select_user_and_type),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            line = Int.MAX_VALUE,
            modifier = Modifier
                .fillMaxWidth(0.5f)
        )
    }
}