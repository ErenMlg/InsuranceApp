package com.softcross.insuranceapp.presentation.customer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.CurrentUser
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.common.UserCustomers
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCustomersViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
) : ViewModel() {

    private val _customerState =
        mutableStateOf<ScreenState<List<Customer>>>(ScreenState.Loading)
    val customerState: State<ScreenState<List<Customer>>> = _customerState

    private fun getUserCustomers() = viewModelScope.launch {
        customerRepository.getUserCustomers(CurrentUser.getCurrentUserID()).collect { response ->
            if (response is NetworkResponseState.Success) {
                UserCustomers.setCustomerList(response.result)
            }
        }
    }

    fun deleteCustomer(id: String) = viewModelScope.launch {
        customerRepository.deleteCustomer(id).collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    getUserCustomers()
                }

                is NetworkResponseState.Error -> {
                    _customerState.value =
                        ScreenState.Error(response.exception.message ?: "An error catched")
                }

                is NetworkResponseState.Loading -> {
                    _customerState.value = ScreenState.Loading
                }
            }
        }
    }

}