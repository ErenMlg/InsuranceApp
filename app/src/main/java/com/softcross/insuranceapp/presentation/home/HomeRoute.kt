package com.softcross.insuranceapp.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.presentation.components.CustomText
import com.softcross.insuranceapp.presentation.theme.InsuranceAppTheme

@Composable
fun HomeRoute() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeSmallButtons(
                    modifier = Modifier.weight(0.5f).padding(end = 8.dp),
                    icon = R.drawable.icon_add_customer,
                    title = R.string.new_customer
                )
                HomeSmallButtons(
                    modifier = Modifier.weight(0.5f).padding(start = 8.dp),
                    icon = R.drawable.icon_add_policy,
                    title = R.string.new_policy
                )
            }
            HomeLargeButtons(
                icon = R.drawable.icon_customer,
                title = R.string.my_customers,
                text = R.string.my_customers_text
            )
            HomeLargeButtons(
                icon = R.drawable.icon_policy,
                title = R.string.my_policies,
                text = R.string.my_policies_text
            )
            HomeLargeButtons(
                icon = R.drawable.icon_payments,
                title = R.string.my_payments,
                text = R.string.my_payments_text
            )
        }
    }
}

@Composable
fun HomeSmallButtons(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes title: Int,
){
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = title),
                modifier = Modifier.padding(
                    bottom = 8.dp,
                    top = 16.dp
                )
            )
            CustomText(
                text = stringResource(id = title),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp,
                fontFamilyID = R.font.poppins_semi_bold,
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 16.dp
                )
            )
        }
    }
}

@Composable
fun HomeLargeButtons(
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes text: Int,
) {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = title),
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                CustomText(
                    text = stringResource(id = title),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp,
                    fontFamilyID = R.font.poppins_semi_bold,
                    modifier = Modifier.padding(8.dp)
                )
                CustomText(
                    text = stringResource(id = text),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                    line = Int.MAX_VALUE,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeRoutePreview() {
    InsuranceAppTheme {
        HomeRoute()
    }
}