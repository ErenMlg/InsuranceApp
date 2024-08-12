package com.softcross.insuranceapp.navigation

import com.softcross.insuranceapp.R

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
) {
    data object HomeScreen : BottomNavItem(
        title = "Home",
        icon = R.drawable.icon_dashboard,
        route = "home"
    )

    data object CustomersScreen : BottomNavItem(
        title = "Customers",
        icon = R.drawable.icon_customer,
        route = "customers"
    )

    data object PoliciesScreen : BottomNavItem(
        title = "Policies",
        icon = R.drawable.icon_policy,
        route = "policies"
    )

    data object PaymentsScreen : BottomNavItem(
        title = "Payments",
        icon = R.drawable.icon_payments,
        route = "payments"
    )
}

