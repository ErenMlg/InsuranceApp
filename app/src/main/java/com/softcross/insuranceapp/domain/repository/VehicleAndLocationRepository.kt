package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.domain.model.Make
import com.softcross.insuranceapp.domain.model.Model
import com.softcross.insuranceapp.domain.model.Province
import kotlinx.coroutines.flow.Flow

interface VehicleAndLocationRepository {

    fun getAllProvinceAndDistricts(): Flow<NetworkResponseState<List<Province>>>

    fun getCarMakes(): Flow<NetworkResponseState<List<Make>>>

    fun getCarYears(): Flow<NetworkResponseState<List<Int>>>

    fun getCarModels(year: Int, make: Int): Flow<NetworkResponseState<List<Model>>>

}