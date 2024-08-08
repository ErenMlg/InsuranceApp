package com.softcross.insuranceapp.data.api

import com.softcross.insuranceapp.data.dto.car_properties.MakesResponse
import com.softcross.insuranceapp.data.dto.car_properties.ModelsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CarPropertiesService {

    @GET("makes")
    suspend fun getCarMakes(): MakesResponse

    @GET("years")
    suspend fun getCarYears(): List<Int>

    @GET("models")
    suspend fun getCarModels(@Query("year") year: Int, @Query("make_id") make: Int): ModelsResponse

}