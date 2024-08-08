package com.softcross.insuranceapp.presentation.policies.new_policy

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Kasko
import com.softcross.insuranceapp.domain.model.Traffic
import com.softcross.insuranceapp.domain.model.Makes
import com.softcross.insuranceapp.domain.model.Models
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.repository.PolicyTypeRepository
import com.softcross.insuranceapp.domain.repository.PolicyRepository
import com.softcross.insuranceapp.domain.repository.VehicleAndLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPolicyViewModel @Inject constructor(
    private val vehicleAndLocationRepository: VehicleAndLocationRepository,
    private val policyRepository: PolicyRepository,
    private val policyTypeRepository: PolicyTypeRepository,
) : ViewModel() {

    private val _makesState = mutableStateOf<ScreenState<List<Makes>>>(ScreenState.Loading)
    val makesState: State<ScreenState<List<Makes>>> = _makesState

    private val _modelsState = mutableStateOf<ScreenState<List<Models>>>(ScreenState.Loading)
    val modelState: State<ScreenState<List<Models>>> = _modelsState

    private val _policyState = mutableStateOf<ScreenState<Policy>>(ScreenState.Loading)
    val policyState: State<ScreenState<Policy>> = _policyState

    private val _typeState = mutableStateOf<ScreenState<Boolean>>(ScreenState.Loading)
    val typeState: State<ScreenState<Boolean>> = _typeState

    init {
        getMakes()
    }

    fun addPolicy(policy: Policy) = viewModelScope.launch {
        policyRepository.addPolicy(policy).collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _policyState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _policyState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                }

                is NetworkResponseState.Loading -> {
                    _policyState.value = ScreenState.Loading
                }
            }
        }
    }

    fun updatePolicy(policy: Policy) = viewModelScope.launch {

    }

    fun addTraffic(traffic: Traffic) = viewModelScope.launch {
        policyTypeRepository.addTraffic(traffic).collect {
            if (it is NetworkResponseState.Error) {
                _typeState.value = ScreenState.Error(it.exception.message ?: "An error occurred")
            }
        }
    }

    fun addKasko(kasko: Kasko) = viewModelScope.launch {
        policyTypeRepository.addKasko(kasko).collect {
            if (it is NetworkResponseState.Error) {
                _typeState.value = ScreenState.Error(it.exception.message ?: "An error occurred")
            }
        }
    }

    fun addHealth(health: Health) = viewModelScope.launch {
        policyTypeRepository.addHealth(health).collect {
            if (it is NetworkResponseState.Error) {
                _typeState.value = ScreenState.Error(it.exception.message ?: "An error occurred")
            }
        }
    }

    fun addDask(dask: Dask) = viewModelScope.launch {
        policyTypeRepository.addDask(dask).collect {
            if (it is NetworkResponseState.Error) {
                _typeState.value = ScreenState.Error(it.exception.message ?: "An error occurred")
            }
        }
    }

    private fun getMakes() = viewModelScope.launch {
        vehicleAndLocationRepository.getCarMakes().collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _makesState.value = ScreenState.Success(response.result)
                }

                is NetworkResponseState.Error -> {
                    _makesState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                }

                is NetworkResponseState.Loading -> {
                    _makesState.value = ScreenState.Loading
                }
            }
        }
    }

    fun getModels(makeId: Int, year: Int) = viewModelScope.launch {
        vehicleAndLocationRepository.getCarModels(year, makeId).collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _modelsState.value = ScreenState.Success(response.result)
                    println("Models: ${response.result}")
                }

                is NetworkResponseState.Error -> {
                    _modelsState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                    println("Error: ${response.exception.message}")
                }

                is NetworkResponseState.Loading -> {
                    _modelsState.value = ScreenState.Loading
                }
            }
        }
    }

}