package com.softcross.insuranceapp.presentation.customer.edit_customer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _newCustomerState =
        mutableStateOf<ScreenState<Customer>>(ScreenState.Loading)
    val newCustomerState: State<ScreenState<Customer>> = _newCustomerState

    private val _oldCustomerState =
        mutableStateOf<ScreenState<Customer>>(ScreenState.Loading)
    val oldCustomerState: State<ScreenState<Customer>> = _oldCustomerState

    init {
        savedStateHandle.get<String>("id")?.let { customerID ->
            getCustomer(customerID)
        }
    }

    private fun getCustomer(customerID: String) = viewModelScope.launch {
        customerRepository.getCustomer(customerID).collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _oldCustomerState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _oldCustomerState.value =
                        ScreenState.Error(response.exception.message ?: "An error")
                }

                is NetworkResponseState.Loading -> {
                    _oldCustomerState.value = ScreenState.Loading
                }
            }
        }
    }

    fun updateCustomer(customer: Customer) = viewModelScope.launch {
        customerRepository.updateCustomer(customer).collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _newCustomerState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _newCustomerState.value =
                        ScreenState.Error(response.exception.message ?: "An error")
                }

                is NetworkResponseState.Loading -> {
                    _newCustomerState.value = ScreenState.Loading
                }
            }
        }
    }

}