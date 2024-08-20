package com.softcross.insuranceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.softcross.insuranceapp.navigation.BottomNavigationBar
import com.softcross.insuranceapp.navigation.Customers
import com.softcross.insuranceapp.navigation.EditCustomer
import com.softcross.insuranceapp.navigation.ForgetPassword
import com.softcross.insuranceapp.navigation.Home
import com.softcross.insuranceapp.navigation.InsuranceNavHost
import com.softcross.insuranceapp.navigation.Login
import com.softcross.insuranceapp.navigation.NewCustomer
import com.softcross.insuranceapp.navigation.NewPayment
import com.softcross.insuranceapp.navigation.NewPolicy
import com.softcross.insuranceapp.navigation.Payments
import com.softcross.insuranceapp.navigation.Policies
import com.softcross.insuranceapp.navigation.Splash
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            InsuranceAppTheme {
                val color = MaterialTheme.colorScheme.primary.toArgb()
                window.statusBarColor = color
                window.navigationBarColor = color
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                when (navBackStackEntry?.destination?.route) {
                    Home.route -> bottomBarState.value = true
                    Customers.route -> bottomBarState.value = true
                    Policies.route -> bottomBarState.value = true
                    Payments.route -> bottomBarState.value = true
                    else -> bottomBarState.value = false
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            bottomBarState = bottomBarState
                        )
                    }
                ) { innerPadding ->
                    InsuranceNavHost(
                        modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}