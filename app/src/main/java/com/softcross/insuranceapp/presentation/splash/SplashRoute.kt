package com.softcross.insuranceapp.presentation.splash

import android.content.Context
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.presentation.components.CustomText

@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    onEntryNavigate: () -> Unit,
    onLogged: () -> Unit
) {
    val context = LocalContext.current
    val makesState = viewModel.makeState.value
    val addressState = viewModel.addressState.value
    val userState = viewModel.userState.value
    val stayLogged by remember {
        mutableStateOf(
            context.getSharedPreferences("logFile", Context.MODE_PRIVATE)
                .getBoolean("stayLogged", false)
        )
    }

    if (stayLogged) {
        val loggedUser = context.getSharedPreferences("logFile", Context.MODE_PRIVATE)
            .getString("userID", "") ?: ""
        viewModel.getUserWithID(loggedUser)
    }

    LaunchedEffect(makesState, addressState, userState) {
        if (makesState is ScreenState.Success && addressState is ScreenState.Success) {
            if (stayLogged) {
                if (userState.errorMessage.isEmpty() && userState.isLoading.not()) {
                    onLogged()
                } else if (userState.errorMessage.isNotEmpty() && userState.isLoading.not()) {
                    Toast.makeText(context, userState.errorMessage, Toast.LENGTH_SHORT).show()
                }
            } else {
                onEntryNavigate()
            }
        }
    }
    SplashAnimation()

}

@Composable
fun SplashAnimation() {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .scale(scale.value)
        )
        CustomText(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            fontFamilyID = R.font.poppins_medium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        CircularProgressIndicator()
    }
}
