package com.softcross.insuranceapp.presentation.policies.new_policy.policy_forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.PolicyType
import com.softcross.insuranceapp.presentation.components.CustomLargeIconButton
import com.softcross.insuranceapp.presentation.components.CustomText


@Composable
fun HealthPolicyForm(
    addedPolicy: Policy?,
    onTakeOfferClick: (Int, Int) -> Unit,
    onHealthCreate: (Health) -> Unit
) {

    var smoke by remember { mutableStateOf(false) }
    var alcohol by remember { mutableStateOf(false) }
    var drugs by remember { mutableStateOf(false) }
    var sport by remember { mutableStateOf(false) }
    var surgery by remember { mutableStateOf(false) }
    var allergy by remember { mutableStateOf(false) }
    var price by remember { mutableIntStateOf(500) }
    var showedPrice by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = addedPolicy) {
        if (addedPolicy != null) {
            onHealthCreate(
                Health(
                    policyNo = addedPolicy.policyNo,
                    smoke = smoke,
                    alcohol = alcohol,
                    drugs = drugs,
                    sport = sport,
                    surgery = surgery,
                    allergy = allergy
                )
            )
        }
    }

    Column {
        CustomText(
            text = "Health",
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            SwipeRow("Smoke", smoke) { smoke = it; if (it) price += 500 else price -= 500 }
            HorizontalDivider()
            SwipeRow("Alcohol", alcohol) { alcohol = it; if (it) price += 500 else price -= 500 }
            HorizontalDivider()
            SwipeRow("Drugs", drugs) { drugs = it; if (it) price += 1000 else price -= 1000 }
            HorizontalDivider()
            SwipeRow("Sport", sport) { sport = it; if (it) price -= 1000 else price += 1000 }
            HorizontalDivider()
            SwipeRow("Surgery", surgery) { surgery = it; if (it) price += 250 else price -= 250 }
            HorizontalDivider()
            SwipeRow("Allergy", allergy) { allergy = it; if (it) price += 100 else price -= 100 }
            AnimatedVisibility(visible = addedPolicy != null) {
                CustomText(
                    text = "Price: $showedPrice",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Row {
                CustomLargeIconButton(
                    isEnable = true,
                    onClick = {
                        showedPrice = price
                        onTakeOfferClick(price, PolicyType.HEALTH.getTypeCode())
                    },
                    buttonText = R.string.take_price,
                    id = R.drawable.icon_payments,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 8.dp)
                )
                CustomLargeIconButton(
                    isEnable = addedPolicy != null,
                    onClick = { },
                    buttonText = R.string.new_policy,
                    id = R.drawable.icon_add_policy,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SwipeRow(text: String, data: Boolean, onChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomText(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Switch(
            checked = data,
            onCheckedChange = onChecked,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}