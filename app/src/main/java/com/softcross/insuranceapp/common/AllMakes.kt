package com.softcross.insuranceapp.common

import com.softcross.insuranceapp.domain.model.Make

object AllMakes {

    private var makeList : List<Make> = emptyList()

    fun setMakeList(models: List<Make>) {
        makeList = models
    }

    fun getMakeList() : List<Make> {
        return makeList
    }

    fun getMakeById(id: Int) : Make {
        return makeList.find { it.id == id }!!
    }

}