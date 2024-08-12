package com.softcross.insuranceapp.data.source

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.extensions.httpErrorHandle
import com.softcross.insuranceapp.common.extensions.toCustomerDto
import com.softcross.insuranceapp.common.extensions.toDaskDto
import com.softcross.insuranceapp.common.extensions.toHealthDto
import com.softcross.insuranceapp.common.extensions.toKaskoDto
import com.softcross.insuranceapp.common.extensions.toPaymentDto
import com.softcross.insuranceapp.common.extensions.toPolicyDto
import com.softcross.insuranceapp.common.extensions.toTrafficDto
import com.softcross.insuranceapp.data.api.CarPropertiesService
import com.softcross.insuranceapp.data.api.CustomerService
import com.softcross.insuranceapp.data.api.LocationService
import com.softcross.insuranceapp.data.api.PaymentService
import com.softcross.insuranceapp.data.api.PolicyService
import com.softcross.insuranceapp.data.api.PolicyTypeService
import com.softcross.insuranceapp.data.dto.address.AddressResponseDto
import com.softcross.insuranceapp.data.dto.car_properties.MakesResponse
import com.softcross.insuranceapp.data.dto.car_properties.ModelsResponse
import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.data.dto.customer.CustomerResponse
import com.softcross.insuranceapp.data.dto.dask.DaskDto
import com.softcross.insuranceapp.data.dto.dask.DaskResponse
import com.softcross.insuranceapp.data.dto.health.HealthDto
import com.softcross.insuranceapp.data.dto.health.HealthResponse
import com.softcross.insuranceapp.data.dto.payment.PaymentDto
import com.softcross.insuranceapp.data.dto.payment.PaymentResponse
import com.softcross.insuranceapp.data.dto.policy.PolicyDto
import com.softcross.insuranceapp.data.dto.policy.PolicyResponse
import com.softcross.insuranceapp.data.dto.traffic_kasko.TrafficKaskoDto
import com.softcross.insuranceapp.data.dto.traffic_kasko.TrafficKaskoResponse
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Kasko
import com.softcross.insuranceapp.domain.model.Payment
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.Traffic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val customerService: CustomerService,
    private val locationService: LocationService,
    private val policyService: PolicyService,
    private val policyTypeService: PolicyTypeService,
    private val carPropertiesService: CarPropertiesService,
    private val paymentService: PaymentService
) : RemoteDataSource {

    // Customer

    override fun addCustomer(customer: Customer): Flow<NetworkResponseState<CustomerDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = customerService.addCustomer(customer.toCustomerDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getAllCustomers(): Flow<NetworkResponseState<CustomerResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = customerService.getAllCustomers()
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getCustomerById(id: String): Flow<NetworkResponseState<CustomerDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = customerService.getCustomerById(id)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun updateCustomer(customer: Customer): Flow<NetworkResponseState<CustomerDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = customerService.updateCustomer(customer.id, customer.toCustomerDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun searchCustomer(
        nameKey: String,
        idKey: String
    ): Flow<NetworkResponseState<CustomerResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = customerService.getSearchedCustomers(nameKey, idKey)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun deleteCustomer(id: String): Flow<NetworkResponseState<Unit>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                customerService.deleteCustomer(id)
                emit(NetworkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getUserCustomers(userID: String): Flow<NetworkResponseState<CustomerResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = customerService.getUserCustomers(userID)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    // Address

    override fun getAllProvinceAndDistricts(): Flow<NetworkResponseState<AddressResponseDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = locationService.getAllProvinceAndDistricts()
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    // Policy Types

    override fun addTraffic(traffic: Traffic): Flow<NetworkResponseState<TrafficKaskoDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyTypeService.addTraffic(traffic.toTrafficDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun addKasko(kasko: Kasko): Flow<NetworkResponseState<TrafficKaskoDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyTypeService.addKasko(kasko.toKaskoDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun addHealth(health: Health): Flow<NetworkResponseState<HealthDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyTypeService.addHealth(health.toHealthDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun addDask(dask: Dask): Flow<NetworkResponseState<DaskDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyTypeService.addDask(dask.toDaskDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun makePayment(payment: Payment): Flow<NetworkResponseState<PaymentDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = paymentService.makePayment(payment.toPaymentDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getAllPayments(): Flow<NetworkResponseState<PaymentResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = paymentService.getPayments()
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun deletePayment(id: String): Flow<NetworkResponseState<PaymentDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = paymentService.deletePayment(id)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    // Car Properties

    override fun getCarMakes(): Flow<NetworkResponseState<MakesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = carPropertiesService.getCarMakes()
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getCarYears(): Flow<NetworkResponseState<List<Int>>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = carPropertiesService.getCarYears()
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getCarModels(year: Int, make: Int): Flow<NetworkResponseState<ModelsResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = carPropertiesService.getCarModels(year, make)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    // Policy

    override fun addPolicy(policy: Policy): Flow<NetworkResponseState<PolicyDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyService.addPolicy(policy.toPolicyDto())
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun updatePolicy(policy: Policy): Flow<NetworkResponseState<PolicyDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyService.updatePolicy(policy.policyNo, policy.toPolicyDto())
                println(result)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                println(e)
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun deletePolicy(id: String): Flow<NetworkResponseState<Unit>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                policyService.deletePolicy(id)
                emit(NetworkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getAllPolicies(): Flow<NetworkResponseState<PolicyResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyService.getAllPolicies()
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun getPolicyById(id: String): Flow<NetworkResponseState<PolicyDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyService.getPolicyById(id)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

    override fun searchPolicy(
        idKey: String
    ): Flow<NetworkResponseState<PolicyResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val result = policyService.getSearchedPolicies(idKey)
                emit(NetworkResponseState.Success(result))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e.httpErrorHandle()))
            }
        }
    }

}