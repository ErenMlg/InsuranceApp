package com.softcross.insuranceapp.presentation.payments

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.model.Payment
import com.softcross.insuranceapp.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPaymentsViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _paymentState = mutableStateOf<ScreenState<List<Payment>>>(ScreenState.Loading)
    val paymentState = _paymentState

    init {
        getAllPayments()
    }

    fun getAllPayments() = viewModelScope.launch {
        paymentRepository.getAllPayments().collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _paymentState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _paymentState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                }

                is NetworkResponseState.Loading -> {
                    _paymentState.value = ScreenState.Loading
                }
            }
        }
    }

    fun deletePayment(id:String) = viewModelScope.launch {
        paymentRepository.deletePayment(id).collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    getAllPayments()
                }

                is NetworkResponseState.Error -> {
                    _paymentState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                }

                is NetworkResponseState.Loading -> {
                    _paymentState.value = ScreenState.Loading
                }
            }
        }
    }

}