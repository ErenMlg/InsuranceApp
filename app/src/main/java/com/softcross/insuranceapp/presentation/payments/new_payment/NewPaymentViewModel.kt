package com.softcross.insuranceapp.presentation.payments.new_payment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.model.Payment
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.repository.PaymentRepository
import com.softcross.insuranceapp.domain.repository.PolicyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPaymentViewModel @Inject constructor(
    private val policyRepository: PolicyRepository,
    private val paymentRepository: PaymentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _policyState = mutableStateOf<ScreenState<Policy>>(ScreenState.Loading)
    val policyState get() = _policyState

    private val _paymentState = mutableStateOf<ScreenState<Payment>>(ScreenState.Loading)
    val paymentState get() = _paymentState

    init {
        savedStateHandle.get<String>("id")?.let { policyID ->
            getPolicy(policyID)
        }
    }

    private fun getPolicy(policyID: String) = viewModelScope.launch {
        policyRepository.getPolicyById(id = policyID).collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _policyState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _policyState.value = ScreenState.Error(response.exception.message ?: "Error")
                }

                is NetworkResponseState.Loading -> {
                    _policyState.value = ScreenState.Loading
                }
            }
        }
    }

    fun updatePolicy(policy: Policy) = viewModelScope.launch {
        policyRepository.updatePolicy(policy).collectLatest { }
    }

    fun makePayment(payment: Payment) = viewModelScope.launch {
        paymentRepository.makePayment(payment).collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _paymentState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _paymentState.value = ScreenState.Error(response.exception.message ?: "Error")
                }

                is NetworkResponseState.Loading -> {
                    _paymentState.value = ScreenState.Loading
                }
            }
        }
    }


}