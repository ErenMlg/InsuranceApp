package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Kasko
import com.softcross.insuranceapp.domain.model.Traffic
import kotlinx.coroutines.flow.Flow

interface PolicyTypeRepository {

    fun addTraffic(traffic: Traffic): Flow<NetworkResponseState<Traffic>>

    fun addKasko(kasko: Kasko): Flow<NetworkResponseState<Kasko>>

    fun addDask(dask: Dask): Flow<NetworkResponseState<Dask>>

    fun addHealth(health: Health): Flow<NetworkResponseState<Health>>

}