package com.softcross.insuranceapp.common

import com.softcross.insuranceapp.domain.model.Customer

object AllCustomers {

    private var customerList: List<Customer> = emptyList()

    fun setCustomerList(customers: List<Customer>) {
        customerList = customers
    }

    fun getCustomerList(): List<Customer> {
        return customerList
    }

    fun getCustomerById(id: String): Customer? {
        return customerList.find { it.id == id }
    }

    fun getCustomerByIndex(index: Int): Customer {
        return customerList[index]
    }

    fun getCustomerByName(name: String): Customer? {
        return customerList.find { it.name + " " + it.surname == name }
    }

}