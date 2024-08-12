package com.softcross.insuranceapp.data.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.extensions.mapResponse
import com.softcross.insuranceapp.common.extensions.toPolicy
import com.softcross.insuranceapp.data.source.RemoteDataSource
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.repository.PolicyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PolicyRepository {

    override fun addPolicy(policy: Policy): Flow<NetworkResponseState<Policy>> =
        remoteDataSource.addPolicy(policy).map { it.mapResponse { toPolicy() } }

    override fun updatePolicy(policy: Policy): Flow<NetworkResponseState<Policy>> =
        remoteDataSource.updatePolicy(policy).map { it.mapResponse { toPolicy() } }

    override fun deletePolicy(policy: Policy): Flow<NetworkResponseState<Unit>> =
        remoteDataSource.deletePolicy(policy.policyNo)


    override fun getPolicies(): Flow<NetworkResponseState<List<Policy>>> =
        remoteDataSource.getAllPolicies()
            .map { it.mapResponse { data.map { data -> data.toPolicy() } } }

    override fun getPolicyById(id: String): Flow<NetworkResponseState<Policy>> =
        remoteDataSource.getPolicyById(id).map { it.mapResponse { toPolicy() } }

    override fun searchPolicy(idKey: String): Flow<NetworkResponseState<List<Policy>>> =
        remoteDataSource.searchPolicy(idKey)
            .map { it.mapResponse { data.map { data -> data.toPolicy() } } }


}