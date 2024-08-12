package com.softcross.insuranceapp.data.api

import com.softcross.insuranceapp.data.dto.payment.PaymentDto
import com.softcross.insuranceapp.data.dto.payment.PaymentResponse
import com.softcross.insuranceapp.domain.model.Payment
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentService {

    @POST("payments")
    suspend fun makePayment(@Body paymentDto: PaymentDto) : PaymentDto

    @GET("payments")
    suspend fun getPayments() : PaymentResponse

    @DELETE("payments/{id}")
    suspend fun deletePayment(@Path("id") id:String) : PaymentDto

}