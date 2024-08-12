package com.softcross.insuranceapp.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Splash : Destination {
    override val route: String = "splash"
}

object Login : Destination {
    override val route: String = "login"
}

object ForgetPassword : Destination {
    override val route: String = "forgetPassword"
}

object Home : Destination {
    override val route: String = "home"
}

object Policies : Destination {
    override val route: String = "policies"
}

object NewPolicy : Destination {
    override val route: String = "newPolicy"
}

object Customers : Destination {
    override val route: String = "customers"
}

object NewCustomer : Destination {
    override val route: String = "newCustomer"
}

object EditCustomer : Destination {
    override val route: String = "editCustomer"
    val routeWithArgs = "${route}/{$ARGS_ID}"
    val arguments = listOf(
        navArgument(ARGS_ID) {
            type = NavType.StringType
        }
    )
}

object Payments : Destination {
    override val route: String = "payments"
}

object NewPayment : Destination {
    override val route: String = "newPayment"
    val routeWithArgs = "${route}/{$ARGS_ID}"
    val arguments = listOf(
        navArgument(ARGS_ID) {
            type = NavType.StringType
        }
    )
}

const val ARGS_ID = "id"