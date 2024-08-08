package com.softcross.insuranceapp.data.dto.dask

import com.google.gson.annotations.SerializedName

data class DaskResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<DaskDto>,
)
