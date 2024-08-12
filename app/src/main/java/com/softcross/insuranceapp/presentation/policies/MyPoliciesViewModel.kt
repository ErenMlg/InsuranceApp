package com.softcross.insuranceapp.presentation.policies

import android.net.Network
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.domain.repository.PolicyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPoliciesViewModel @Inject constructor(
    private val policyRepository: PolicyRepository
) : ViewModel() {

    private val _policyState = mutableStateOf<ScreenState<List<Policy>>>(ScreenState.Loading)
    val policyState: State<ScreenState<List<Policy>>> = _policyState

    init {
        getAllPolicies()
    }

    fun getAllPolicies() = viewModelScope.launch {
        policyRepository.getPolicies().collectLatest { response ->
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

    fun searchPolicy(id: String, type: PolicyType = PolicyType.UNSELECTED) = viewModelScope.launch {
        policyRepository.searchPolicy(id).collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    if (type == PolicyType.UNSELECTED) {
                        _policyState.value = ScreenState.Success(response.result)
                    } else {
                        _policyState.value =
                            ScreenState.Success(response.result.filter { it.policyTypeCode == type.getTypeCode() })
                    }
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

    fun deletePolicy(policy: Policy) = viewModelScope.launch {
        policyRepository.deletePolicy(policy).collectLatest {
            when (it) {
                is NetworkResponseState.Success -> {
                    getAllPolicies()
                }

                is NetworkResponseState.Error -> {
                    _policyState.value = ScreenState.Error(it.exception.message ?: "Error")
                }

                is NetworkResponseState.Loading -> {
                    _policyState.value = ScreenState.Loading
                }
            }
        }
    }
}