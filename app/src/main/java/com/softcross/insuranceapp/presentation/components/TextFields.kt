package com.softcross.insuranceapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R

@Composable
fun CustomTextField(
    givenValue: String,
    placeHolder: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    regex: String.() -> Boolean
) {
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