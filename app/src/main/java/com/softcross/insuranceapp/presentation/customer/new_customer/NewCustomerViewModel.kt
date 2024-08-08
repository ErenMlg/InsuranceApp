package com.softcross.insuranceapp.presentation.customer.new_customer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Province
import com.softcross.insuranceapp.domain.repository.CustomerRepository
import com.softcross.insuranceapp.domain.repository.VehicleAndLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val vehicleAndLocationRepository: VehicleAndLocationRepository
) : ViewModel() {

    private val _newCustomerState =
        mutableStateOf<ScreenState<CustomerDto>>(ScreenState.Loading)
    val newCustomerState: State<ScreenState<CustomerDto>> = _newCustomerState

    private val _addressState = mutableStateOf<ScreenState<List<Province>>>(ScreenState.Loading)
    val addressState: State<ScreenState<List<Province>>> = _addressState

    init {
        getAllProvinces()
    }

    private fun getAllProvinces() = viewModelScope.launch {
        vehicleAndLocationRepository.getAllProvinceAndDistricts().collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _addressState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _addressState.value = ScreenState.Error(response.exception.message ?: "An error")
                }

                is NetworkResponseState.Loading -> {
                    _addressState.value = ScreenState.Loading
                }
            }
        }
    }

    fun addCustomer(customer: Customer) = viewModelScope.launch {
        customerRepository.addCustomer(customer).collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _newCustomerState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _newCustomerState.value = ScreenState.Error(response.exception.message ?: "An error")
                }

                is NetworkResponseState.Loading -> {
                    _newCustomerState.value = ScreenState.Loading
                }
            }
        }
    }

}