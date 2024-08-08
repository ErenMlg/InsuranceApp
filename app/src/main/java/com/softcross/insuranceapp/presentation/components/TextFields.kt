package com.softcross.insuranceapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.softcross.insuranceapp.R
import com.softcross.insuranceapp.common.extensions.noRippleClickable
import com.softcross.insuranceapp.common.extensions.passwordRegex
import com.softcross.insuranceapp.common.extensions.toDate
import java.util.Date
import kotlin.math.abs

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    givenValue: String,
    placeHolder: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    regex: String.() -> Boolean,
    enabled: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            placeholder = {
                CustomText(text = placeHolder, fontSize = 14.sp, color = Color.Gray)
            },
            enabled = enabled,
            isError = !givenValue.regex(),
            trailingIcon = trailingIcon,
            value = givenValue,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Gray,
                errorCursorColor = Color.Gray,
                unfocusedTextColor = Color.Gray,
                errorTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
                errorTrailingIconColor = MaterialTheme.colorScheme.secondary,
                disabledTextColor = Color.Gray
            ),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 14.sp
            ),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(2.dp, RoundedCornerShape(8.dp)),
            onValueChange = onValueChange,
        )
        AnimatedVisibility(
            visible = !givenValue.regex() && givenValue.isNotEmpty(),
            enter = slideInVertically() + expandVertically() + fadeIn(),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            CustomText(
                text = errorMessage, modifier = Modifier.fillMaxWidth(),
                fontSize = 10.sp, color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun CustomSelectionDialog(
    modifier: Modifier = Modifier,
    data: List<String>,
    placeHolder: String,
    enabled: Boolean = true,
    onDataSelected: (String) -> Unit,
    title: String
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedData by remember { mutableStateOf("") }
    val state = rememberLazyListState()

    val centerItem by remember {
        derivedStateOf {
            val layoutInfo = state.layoutInfo
            val centerOffset = layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2
            layoutInfo.visibleItemsInfo.minByOrNull {
                val itemCenter = it.offset + it.size / 2
                abs(itemCenter - centerOffset)
            }?.index ?: 0
        }
    }

    LaunchedEffect(key1 = showDialog) {
        if (showDialog) state.scrollToItem(0)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    CustomText(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                    LazyColumn(
                        state = state,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Spacer(modifier = Modifier.padding(((LocalConfiguration.current.screenHeightDp * 0.5) * 0.20).dp))
                        }
                        if (data.isEmpty()){
                            item {
                                CustomText(
                                    text = "No data available",
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        items(data.size) { item ->
                            CustomText(
                                text = data[item],
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .graphicsLayer {
                                        rotationX = when (item) {
                                            centerItem -> 0f
                                            else -> (centerItem - item) * 7.25f
                                        }

                                    }
                                    .noRippleClickable {
                                        selectedData = data[item]
                                        onDataSelected(selectedData)
                                        showDialog = false
                                    }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.padding(((LocalConfiguration.current.screenHeightDp * 0.5) * 0.20).dp))
                        }
                    }
                }
            }
        }
    }

    CustomTextField(
        givenValue = selectedData,
        placeHolder = placeHolder,
        onValueChange = { selectedData = it },
        regex = String::isNotEmpty,
        enabled = false,
        modifier = modifier
            .then(
            if (enabled) Modifier.noRippleClickable {
                showDialog = true
            } else {
                Modifier
            }
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateTimePicker(
    placeHolder: String,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }
    val datePickerState =
        rememberDatePickerState(
            yearRange = 1930..2006
        )

    if (showDatePicker) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                dayContentColor = MaterialTheme.colorScheme.tertiary,
            ),
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        date = datePickerState.selectedDateMillis?.toDate() ?: ""
                        onDateSelected(date)
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    CustomText(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    CustomText(text = "Dismiss")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                    dayContentColor = MaterialTheme.colorScheme.tertiary,
                    weekdayContentColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }


    CustomTextField(
        givenValue = date,
        placeHolder = placeHolder,
        onValueChange = { date = it },
        regex = String::isNotEmpty,
        enabled = false,
        modifier = Modifier.noRippleClickable {
            showDatePicker = true
        }
    )
}


@Composable
fun CustomPasswordTextField(
    givenValue: String,
    placeHolder: String,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            placeholder = {
                Text(
                    text = placeHolder,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            },
            isError = !givenValue.passwordRegex(),
            value = givenValue,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Gray,
                errorCursorColor = Color.Gray,
                unfocusedTextColor = Color.Gray,
                errorTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
                errorTrailingIconColor = MaterialTheme.colorScheme.secondary,
            ),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 14.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (passwordVisible) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_hide_password),
                        contentDescription = stringResource(id = R.string.hide_password),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.noRippleClickable {
                            passwordVisible = false
                        }
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_show_password),
                        contentDescription = stringResource(id = R.string.show_password),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.noRippleClickable {
                            passwordVisible = true
                        }
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(2.dp, RoundedCornerShape(8.dp)),
            onValueChange = onValueChange,
        )
        AnimatedVisibility(
            visible = !givenValue.passwordRegex() && givenValue.isNotEmpty(),
            enter = slideInVertically() + expandVertically() + fadeIn(),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            PasswordChecker(password = givenValue)
        }
    }
}

@Composable
fun PasswordChecker(
    password: String
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.password_error_title),
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.error
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (password.length >= 8) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_one),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if ("([0-9])".toRegex().containsMatchIn(password)) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_two),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if ("([a-z])".toRegex().containsMatchIn(password)) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_three),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if ("([A-Z])".toRegex().containsMatchIn(password)) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_four),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}