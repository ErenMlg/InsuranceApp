package com.softcross.insuranceapp.data.api

import com.softcross.insuranceapp.data.dto.address.AddressResponseDto
import retrofit2.http.GET

interface LocationService {

    @GET("provinces")
    suspend fun getAllProvinceAndDistricts(): AddressResponseDto

}