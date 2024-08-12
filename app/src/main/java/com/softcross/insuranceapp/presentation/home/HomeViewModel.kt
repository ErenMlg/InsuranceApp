package com.softcross.insuranceapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.AllCustomers
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
class HomeViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
) : ViewModel() {

    private val _customerState =
        mutableStateOf<ScreenState<List<Customer>>>(ScreenState.Loading)
    val customerState: State<ScreenState<List<Customer>>> = _customerState

    private val _allCustomerState =
        mutableStateOf<ScreenState<List<Customer>>>(ScreenState.Loading)
    val allCustomerState: State<ScreenState<List<Customer>>> = _allCustomerState


    init {
        getUserCustomers()
        getAllCustomers()
    }

    private fun getUserCustomers() = viewModelScope.launch {
        customerRepository.getUserCustomers(CurrentUser.getCurrentUserID()).collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _customerState.value = ScreenState.Success(response.result)
                    UserCustomers.setCustomerList(response.result)
                }

                is NetworkResponseState.Error -> {
                    _customerState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                }

                is NetworkResponseState.Loading -> {
                    _customerState.value = ScreenState.Loading
                }
            }
        }
    }

    private fun getAllCustomers() = viewModelScope.launch {
        customerRepository.getAllCustomers().collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _allCustomerState.value = ScreenState.Success(response.result)
                    AllCustomers.setCustomerList(response.result)
                }

                is NetworkResponseState.Error -> {
                    _allCustomerState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                }

                is NetworkResponseState.Loading -> {
                    _allCustomerState.value = ScreenState.Loading
                }
            }
        }
    }

}