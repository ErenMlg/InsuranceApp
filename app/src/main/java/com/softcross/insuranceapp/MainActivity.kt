package com.softcross.insuranceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.softcross.insuranceapp.presentation.customer.MyCustomersRoute
import com.softcross.insuranceapp.presentation.customer.new_customer.NewCustomerForm
import com.softcross.insuranceapp.presentation.customer.new_customer.NewCustomerRoute
import com.softcross.insuranceapp.presentation.customer.new_customer.NewCustomerRoutePreview
import com.softcross.insuranceapp.presentation.login.LoginRoute
import com.softcross.insuranceapp.presentation.login.reset_password.ResetPasswordRoute
import com.softcross.insuranceapp.presentation.policies.new_policy.NewPolicyRoute
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InsuranceAppTheme {
                val color = MaterialTheme.colorScheme.primary.toArgb()
                window.statusBarColor = color
                window.navigationBarColor = color
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    NewPolicyRoute(Modifier.padding(innerPadding))
                }
            }
        }
    }
}