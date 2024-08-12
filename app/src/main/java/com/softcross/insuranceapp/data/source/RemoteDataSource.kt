package com.softcross.insuranceapp.data.source

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.data.dto.address.AddressResponseDto
import com.softcross.insuranceapp.data.dto.car_properties.MakesResponse
import com.softcross.insuranceapp.data.dto.car_properties.ModelsResponse
import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.data.dto.customer.CustomerResponse
import com.softcross.insuranceapp.data.dto.dask.DaskDto
import com.softcross.insuranceapp.data.dto.health.HealthDto
import com.softcross.insuranceapp.data.dto.payment.PaymentDto
import com.softcross.insuranceapp.data.dto.payment.PaymentResponse
import com.softcross.insuranceapp.data.dto.policy.PolicyDto
import com.softcross.insuranceapp.data.dto.policy.PolicyResponse
import com.softcross.insuranceapp.data.dto.traffic_kasko.TrafficKaskoDto
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Kasko
import com.softcross.insuranceapp.domain.model.Payment
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.Traffic
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //Customer

    fun addCustomer(customer: Customer): Flow<NetworkResponseState<CustomerDto>>

    fun getAllCustomers(): Flow<NetworkResponseState<CustomerResponse>>

    fun getCustomerById(id: String): Flow<NetworkResponseState<CustomerDto>>

    fun updateCustomer(customer: Customer): Flow<NetworkResponseState<CustomerDto>>

    fun searchCustomer(
        nameKey: String,
        idKey: String
    ): Flow<NetworkResponseState<CustomerResponse>>

    fun deleteCustomer(id: String): Flow<NetworkResponseState<Unit>>

    fun getUserCustomers(userID: String): Flow<NetworkResponseState<CustomerResponse>>

    // Location

    fun getAllProvinceAndDistricts(): Flow<NetworkResponseState<AddressResponseDto>>

    // Car Properties

    fun getCarMakes(): Flow<NetworkResponseState<MakesResponse>>

    fun getCarYears(): Flow<NetworkResponseState<List<Int>>>

    fun getCarModels(year: Int, make: Int): Flow<NetworkResponseState<ModelsResponse>>

    // Policy

    fun addPolicy(policy: Policy): Flow<NetworkResponseState<PolicyDto>>

    fun updatePolicy(policy: Policy): Flow<NetworkResponseState<PolicyDto>>

    fun deletePolicy(id: String): Flow<NetworkResponseState<Unit>>

    fun getAllPolicies(): Flow<NetworkResponseState<PolicyResponse>>

    fun getPolicyById(id: String): Flow<NetworkResponseState<PolicyDto>>

    fun searchPolicy(
        idKey: String
    ): Flow<NetworkResponseState<PolicyResponse>>


    // Policy Types

    fun addTraffic(traffic: Traffic): Flow<NetworkResponseState<TrafficKaskoDto>>

    fun addKasko(kasko: Kasko): Flow<NetworkResponseState<TrafficKaskoDto>>

    fun addHealth(health: Health): Flow<NetworkResponseState<HealthDto>>

    fun addDask(dask: Dask): Flow<NetworkResponseState<DaskDto>>

    // Payment

    fun makePayment(payment: Payment): Flow<NetworkResponseState<PaymentDto>>

    fun getAllPayments(): Flow<NetworkResponseState<PaymentResponse>>

    fun deletePayment(id: String): Flow<NetworkResponseState<PaymentDto>>

}