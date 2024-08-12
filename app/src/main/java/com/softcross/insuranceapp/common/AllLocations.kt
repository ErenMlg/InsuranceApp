package com.softcross.insuranceapp.common

import com.softcross.insuranceapp.domain.model.Province

object AllLocations {

    private var locationList: List<Province> = emptyList()

    fun setLocations(locations: List<Province>) {
        locationList = locations
    }

    fun getLocations(): List<Province> {
        return locationList
    }

    fun getLocationById(id: Int): Province? {
        return locationList.find { it.id == id }
    }

}