package com.softcross.insuranceapp.data.api

import com.softcross.insuranceapp.data.dto.policy.PolicyDto
import com.softcross.insuranceapp.data.dto.policy.PolicyResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PolicyService {

    @POST("policies")
    suspend fun addPolicy(@Body policyDto: PolicyDto): PolicyDto

    @PUT("policies")
    suspend fun updatePolicy(@Body policyDto: PolicyDto): PolicyDto

    @DELETE("policies/{id}")
    suspend fun deletePolicy(@Path("id") id: String)

    @GET("policies")
    suspend fun getAllPolicies(): PolicyResponse

    @GET("policies/customerNo={id}")
    suspend fun getSearchedPolicies(
        @Path("id") idKey: String
    ): PolicyResponse
}