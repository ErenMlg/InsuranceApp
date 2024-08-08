package com.softcross.insuranceapp.common

import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Makes

object TempVariables {

    val customerList = listOf(
        Customer(
            "21863175633", "John Doe",
            surname = "Erdem Molla",
            birthdate = "2006-12-11T22:00:00.000Z",
            email = "freddie.craig@example.com",
            phone = "(571) 694-9034",
            district = "postulant",
            city = "Cougarville"
        ),
        Customer(
            "21863175633", "John Doe",
            surname = "Callie EURO",
            birthdate = "2006-12-11T22:00:00.000Z",
            email = "freddie.craig@example.com",
            phone = "(571) 694-9034",
            district = "postulant",
            city = "Cougarville"
        ),
        Customer(
            "21863175633", "John Doe",
            surname = "Eren Velez",
            birthdate = "2006-12-11T22:00:00.000Z",
            email = "freddie.craig@example.com",
            phone = "(571) 694-9034",
            district = "postulant",
            city = "Cougarville"
        )
    )

    fun findCustomerByName(name: String): Customer? {
        return customerList.find { it.name + " " + it.surname == name }
    }

    val makeList = listOf(
        Makes(24, "Alfa Romeo"),
        Makes(44, "Aston Martin"),
        Makes(2, "Audi"),
        Makes(25, "Bentley"),
        Makes(3, "BMW"),
        Makes(56, "Bugatti")
    )

    val yearList = listOf("2015", "2016", "2017", "2018", "2019", "2020")

}