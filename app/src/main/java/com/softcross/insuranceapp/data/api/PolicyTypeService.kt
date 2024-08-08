package com.softcross.insuranceapp.data.api

import com.softcross.insuranceapp.data.dto.dask.DaskDto
import com.softcross.insuranceapp.data.dto.dask.DaskResponse
import com.softcross.insuranceapp.data.dto.health.HealthDto
import com.softcross.insuranceapp.data.dto.health.HealthResponse
import com.softcross.insuranceapp.data.dto.traffic_kasko.TrafficKaskoDto
import com.softcross.insuranceapp.data.dto.traffic_kasko.TrafficKaskoResponse
import com.softcross.insuranceapp.domain.model.Traffic
import retrofit2.http.Body
import retrofit2.http.POST

interface PolicyTypeService {

    @POST("traffic")
    suspend fun addTraffic(@Body trafficKaskoDto: TrafficKaskoDto): TrafficKaskoDto

    @POST("kasko")
    suspend fun addKasko(@Body trafficKaskoDto: TrafficKaskoDto): TrafficKaskoDto

    @POST("health")
    suspend fun addHealth(@Body healthDto: HealthDto): HealthDto

    @POST("dask")
    suspend fun addDask(@Body daskDto: DaskDto): DaskDto

}