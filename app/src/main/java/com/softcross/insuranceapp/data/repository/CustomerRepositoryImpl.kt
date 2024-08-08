package com.softcross.insuranceapp.data.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.extensions.mapResponse
import com.softcross.insuranceapp.common.extensions.toCustomer
import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.data.source.RemoteDataSource
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : CustomerRepository {

    override fun getAllCustomers(): Flow<NetworkResponseState<List<Customer>>> =
        remoteDataSource.getAllCustomers().map {
            it.mapResponse { data.map { data -> data.toCustomer() } }
        }

    override fun searchCustomer(
        nameKey: String,
        idKey: String
    ): Flow<NetworkResponseState<List<Customer>>> =
        remoteDataSource.searchCustomer(nameKey, idKey).map {
            it.mapResponse { data.map { data -> data.toCustomer() } }
        }

    override fun addCustomer(customer: Customer): Flow<NetworkResponseState<CustomerDto>> =
        remoteDataSource.addCustomer(customer)

    override fun deleteCustomer(id: String): Flow<NetworkResponseState<Unit>> =
        remoteDataSource.deleteCustomer(id)

}