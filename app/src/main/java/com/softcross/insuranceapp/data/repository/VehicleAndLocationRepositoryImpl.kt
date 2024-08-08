package com.softcross.insuranceapp.data.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.extensions.mapResponse
import com.softcross.insuranceapp.common.extensions.toProvince
import com.softcross.insuranceapp.data.source.RemoteDataSource
import com.softcross.insuranceapp.domain.model.Makes
import com.softcross.insuranceapp.domain.model.Models
import com.softcross.insuranceapp.domain.model.Province
import com.softcross.insuranceapp.domain.repository.VehicleAndLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VehicleAndLocationRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : VehicleAndLocationRepository {


    override fun getAllProvinceAndDistricts(): Flow<NetworkResponseState<List<Province>>> =
        remoteDataSource.getAllProvinceAndDistricts().map {
            it.mapResponse { data.map { data -> data.toProvince() } }
        }

    override fun getCarMakes(): Flow<NetworkResponseState<List<Makes>>> =
        remoteDataSource.getCarMakes().map {
            it.mapResponse { data.map { Makes(it.id, it.name) } }
        }

    override fun getCarYears(): Flow<NetworkResponseState<List<Int>>> = remoteDataSource.getCarYears()

    override fun getCarModels(year: Int, make: Int): Flow<NetworkResponseState<List<Models>>> =
        remoteDataSource.getCarModels(year, make)
            .map {
                it.mapResponse { data.map { Models(it.id, it.make_id, it.name) } }
            }

}