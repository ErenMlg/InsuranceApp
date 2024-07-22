package com.softcross.insuranceapp.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp,
    fontFamilyID: Int = R.font.poppins_regular,
    color: Color = Color.DarkGray,
    textAlign: TextAlign = TextAlign.Start,
    line: Int = 1,
    textLayoutResult: ((TextLayoutResult) -> Unit)? = null
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = FontFamily(Font(fontFamilyID)),
        textAlign = textAlign,
        maxLines = line,
        color = color,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = textLayoutResult,
        modifier = modifier,
    )
}