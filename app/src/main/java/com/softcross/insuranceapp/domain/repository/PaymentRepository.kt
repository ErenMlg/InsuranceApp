package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.domain.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {

    fun makePayment(payment: Payment): Flow<NetworkResponseState<Payment>>

    fun getAllPayments(): Flow<NetworkResponseState<List<Payment>>>

    fun deletePayment(id:String): Flow<NetworkResponseState<Payment>>

}