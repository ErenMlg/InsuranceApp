package com.softcross.insuranceapp.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R

@Composable
fun LoadingTextButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isEnable: Boolean,
    onClick: () -> Unit,
    @StringRes buttonText: Int
) {
    Button(
        enabled = isEnable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(50.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                trackColor = MaterialTheme.colorScheme.tertiary,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp)
                    .aspectRatio(1f)
            )
        } else {
            CustomText(
                text = stringResource(id = buttonText),
                fontFamilyID = R.font.poppins_regular,
                fontSize = 14.sp,
                color = if (isEnable) MaterialTheme.colorScheme.background
                else Color.DarkGray
            )
        }
    }
}

@Composable
fun CustomIconButton(
    iconModifier: Modifier = Modifier,
    modifier: Modifier,
    onClick: () -> Unit,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = MaterialTheme.colorScheme.primary
    ),
    @DrawableRes id: Int,
    iconTint: Color = Color.White
) {
    IconButton(
        onClick = onClick,
        colors = colors,
        modifier = modifier
    ) {
        Icon(
            modifier = iconModifier,
            painter = painterResource(id = id),
            contentDescription = "IconButton",
            tint = iconTint
        )
    }
}

@Composable
fun LoadingIconButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnable: Boolean = true,
    onClick: () -> Unit,
    @DrawableRes id: Int,
    @StringRes buttonText: Int
) {
    Button(
        enabled = isEnable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(50.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                trackColor = MaterialTheme.colorScheme.tertiary,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp)
                    .aspectRatio(1f)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                CustomText(
                    text = stringResource(id = buttonText),
                    fontFamilyID = R.font.poppins_regular,
                    fontSize = 14.sp,
                    color = if (isEnable) MaterialTheme.colorScheme.background
                    else Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.9f)
                )
                Icon(
                    painter = painterResource(id = id),
                    contentDescription = "Arrow",
                    tint = if (isEnable) MaterialTheme.colorScheme.background
                    else Color.DarkGray,
                    modifier = Modifier.weight(0.1f).size(24.dp)
                )
            }
        }
    }
}

@Composable
fun CustomLargeIconButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean,
    onClick: () -> Unit,
    @DrawableRes id: Int,
    @StringRes buttonText: Int
) {
    Button(
        enabled = isEnable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CustomText(
                text = stringResource(id = buttonText),
                fontFamilyID = R.font.poppins_regular,
                fontSize = 14.sp,
                color = if (isEnable) MaterialTheme.colorScheme.background
                else Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.9f)
            )
            Icon(
                painter = painterResource(id = id),
                contentDescription = "Arrow",
                tint = if (isEnable) MaterialTheme.colorScheme.background
                else Color.DarkGray,
                modifier = Modifier.weight(0.1f)
            )
        }
    }
}

@Preview
@Composable
fun LoadingTextButtonPreview() {
    LoadingIconButton(
        isLoading = false,
        isEnable = true,
        onClick = {},
        buttonText = R.string.new_policy,
        id = R.drawable.icon_trash
    )
}