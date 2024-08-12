package com.softcross.insuranceapp.data.api

import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.data.dto.customer.CustomerResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerService {

    @POST("customers")
    suspend fun addCustomer(@Body customerDto: CustomerDto): CustomerDto

    @GET("customers")
    suspend fun getAllCustomers(): CustomerResponse

    @GET("customers/{nameKey}/{idKey}")
    suspend fun getSearchedCustomers(
        @Path("nameKey") nameKey: String,
        @Path("idKey") idKey: String
    ): CustomerResponse

    @GET("customers/{id}")
    suspend fun getCustomerById(@Path("id") id: String): CustomerDto

    @PUT("customers/{id}")
    suspend fun updateCustomer(@Path("id") id: String, @Body customerDto: CustomerDto): CustomerDto

    @DELETE("customers/{id}")
    suspend fun deleteCustomer(@Path("id") id: String)

    @GET("customers/agent/{id}")
    suspend fun getUserCustomers(@Path("id") id: String): CustomerResponse

}