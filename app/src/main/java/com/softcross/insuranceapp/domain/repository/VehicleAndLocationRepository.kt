package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.domain.model.Makes
import com.softcross.insuranceapp.domain.model.Models
import com.softcross.insuranceapp.domain.model.Province
import kotlinx.coroutines.flow.Flow

interface VehicleAndLocationRepository {

    fun getAllProvinceAndDistricts(): Flow<NetworkResponseState<List<Province>>>

    fun getCarMakes(): Flow<NetworkResponseState<List<Makes>>>

    fun getCarYears(): Flow<NetworkResponseState<List<Int>>>

    fun getCarModels(year: Int, make: Int): Flow<NetworkResponseState<List<Models>>>

}