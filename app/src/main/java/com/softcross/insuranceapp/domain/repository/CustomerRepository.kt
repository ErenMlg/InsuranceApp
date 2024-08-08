package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    fun getAllCustomers(): Flow<NetworkResponseState<List<Customer>>>

    fun searchCustomer(
        nameKey: String = "",
        idKey: String = ""
    ): Flow<NetworkResponseState<List<Customer>>>

    fun addCustomer(customer: Customer): Flow<NetworkResponseState<CustomerDto>>

    fun deleteCustomer(id: String): Flow<NetworkResponseState<Unit>>
}