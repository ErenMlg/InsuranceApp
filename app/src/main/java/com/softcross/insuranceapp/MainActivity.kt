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
import com.softcross.insuranceapp.presentation.customer.new_customer.NewCustomerRoute
import com.softcross.insuranceapp.presentation.login.LoginRoute
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InsuranceAppTheme {
                val color = MaterialTheme.colorScheme.primary.toArgb()
                window.statusBarColor = color
                window.navigationBarColor = color
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewCustomerRoute(Modifier.padding(innerPadding))
                }
            }
        }
    }
}