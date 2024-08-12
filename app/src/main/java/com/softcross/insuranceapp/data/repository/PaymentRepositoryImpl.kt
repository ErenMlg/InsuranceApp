package com.softcross.insuranceapp.data.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.extensions.mapResponse
import com.softcross.insuranceapp.common.extensions.toPayment
import com.softcross.insuranceapp.data.source.RemoteDataSource
import com.softcross.insuranceapp.domain.model.Payment
import com.softcross.insuranceapp.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PaymentRepository {

    override fun makePayment(payment: Payment): Flow<NetworkResponseState<Payment>> =
        remoteDataSource.makePayment(payment).map { it.mapResponse { toPayment() } }

    override fun getAllPayments(): Flow<NetworkResponseState<List<Payment>>> =
        remoteDataSource.getAllPayments()
            .map { it.mapResponse { data.map { data -> data.toPayment() } } }

    override fun deletePayment(id:String): Flow<NetworkResponseState<Payment>> =
        remoteDataSource.deletePayment(id).map { it.mapResponse { toPayment() } }


}