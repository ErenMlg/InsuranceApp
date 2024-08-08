package com.softcross.insuranceapp.data.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.extensions.mapResponse
import com.softcross.insuranceapp.common.extensions.toDask
import com.softcross.insuranceapp.common.extensions.toHealth
import com.softcross.insuranceapp.common.extensions.toKasko
import com.softcross.insuranceapp.common.extensions.toTraffic
import com.softcross.insuranceapp.data.source.RemoteDataSource
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Kasko
import com.softcross.insuranceapp.domain.model.Traffic
import com.softcross.insuranceapp.domain.repository.PolicyTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PolicyTypeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PolicyTypeRepository {

    override fun addTraffic(traffic: Traffic): Flow<NetworkResponseState<Traffic>> =
        remoteDataSource.addTraffic(traffic).map { it.mapResponse { toTraffic() } }

    override fun addKasko(kasko: Kasko): Flow<NetworkResponseState<Kasko>> =
        remoteDataSource.addKasko(kasko).map { it.mapResponse { toKasko() } }

    override fun addDask(dask: Dask): Flow<NetworkResponseState<Dask>> =
        remoteDataSource.addDask(dask).map { it.mapResponse { toDask() } }

    override fun addHealth(health: Health): Flow<NetworkResponseState<Health>> =
        remoteDataSource.addHealth(health).map { it.mapResponse { toHealth() } }

}