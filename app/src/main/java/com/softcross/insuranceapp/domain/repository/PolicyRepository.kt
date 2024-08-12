package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Policy
import kotlinx.coroutines.flow.Flow

interface PolicyRepository {

    fun addPolicy(policy: Policy): Flow<NetworkResponseState<Policy>>

    fun updatePolicy(policy: Policy): Flow<NetworkResponseState<Policy>>

    fun deletePolicy(policy: Policy): Flow<NetworkResponseState<Unit>>

    fun getPolicies(): Flow<NetworkResponseState<List<Policy>>>

    fun getPolicyById(id: String): Flow<NetworkResponseState<Policy>>

    fun searchPolicy(
        idKey: String = ""
    ): Flow<NetworkResponseState<List<Policy>>>


}