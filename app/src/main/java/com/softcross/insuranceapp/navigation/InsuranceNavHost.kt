package com.softcross.insuranceapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softcross.insuranceapp.presentation.customer.MyCustomersRoute
import com.softcross.insuranceapp.presentation.customer.edit_customer.EditCustomerRoute
import com.softcross.insuranceapp.presentation.customer.new_customer.NewCustomerRoute
import com.softcross.insuranceapp.presentation.home.HomeRoute
import com.softcross.insuranceapp.presentation.login.LoginRoute
import com.softcross.insuranceapp.presentation.login.reset_password.ResetPasswordRoute
import com.softcross.insuranceapp.presentation.payments.MyPaymentsRoute
import com.softcross.insuranceapp.presentation.payments.new_payment.NewPaymentRoute
import com.softcross.insuranceapp.presentation.policies.MyPoliciesRoute
import com.softcross.insuranceapp.presentation.policies.new_policy.NewPolicyRoute
import com.softcross.insuranceapp.presentation.splash.SplashRoute

@Composable
fun InsuranceNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(modifier = modifier, navController = navController, startDestination = Splash.route) {
        composable(route = Splash.route) {
            SplashRoute(
                onEntryNavigate = {
                    navController.navigate(route = Login.route)
                },
                onLogged = {
                    navController.navigate(route = Home.route)
                }
            )
        }

        composable(route = Login.route) {
            LoginRoute(
                onLogin = {
                    navController.navigate(route = Home.route) {
                        popUpTo(Splash.route)
                        launchSingleTop = true
                    }
                },
                onResetPassword = {
                    navController.navigate(route = ForgetPassword.route)
                }
            )
        }

        composable(route = ForgetPassword.route) {
            ResetPasswordRoute()
        }

        composable(route = Customers.route) {
            MyCustomersRoute(
                onEditClick = { id ->
                    navController.navigate(route = "${EditCustomer.route}/${id}")
                }
            )
        }

        composable(route = NewCustomer.route) {
            NewCustomerRoute(
                onHome = {
                    navController.navigate(route = Home.route) {
                        popUpTo(NewCustomer.route)
                    }
                }
            )
        }

        composable(route = EditCustomer.routeWithArgs, arguments = EditCustomer.arguments) {
            EditCustomerRoute(
                onHome = {
                    navController.navigate(route = Home.route) {
                        popUpTo(NewCustomer.route)
                    }
                }
            )
        }

        composable(route = Policies.route) {
            MyPoliciesRoute(
                onPayment = { id ->
                    navController.navigate(route = "${NewPayment.route}/${id}")
                }
            )
        }

        composable(route = NewPolicy.route) {
            NewPolicyRoute(
                onPay = { id ->
                    navController.navigate(route = "${NewPayment.route}/${id}")
                }
            )
        }

        composable(route = Payments.route) {
            MyPaymentsRoute()
        }

        composable(route = NewPayment.routeWithArgs, arguments = NewPayment.arguments) {
            NewPaymentRoute(
                onHome = {
                    navController.navigate(route = Home.route) {
                        popUpTo(NewCustomer.route)
                    }
                }
            )
        }

        composable(route = Home.route) {
            HomeRoute(
                onNewCustomer = {
                    navController.navigate(route = NewCustomer.route)
                },
                onNewPolicy = {
                    navController.navigate(route = NewPolicy.route)
                },
                onMyCustomers = {
                    navController.navigate(route = Customers.route)
                },
                onMyPolicies = {
                    navController.navigate(route = Policies.route)
                },
                onMyPayments = {
                    navController.navigate(route = Payments.route)
                }
            )
        }

    }
}