package com.softcross.insuranceapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.softcross.insuranceapp.R

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp,
    fontFamilyID: Int = R.font.poppins_regular,
    color: Color = MaterialTheme.colorScheme.tertiary,
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

@Composable
fun CustomAnnotatedText(
    text: String,
    header: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp,
    fontFamilyID: Int = R.font.poppins_regular,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start,
    line: Int = 1,
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                )
            ) {
                append(header)
            }
            append(text)
        },
        fontSize = fontSize,
        fontFamily = FontFamily(Font(fontFamilyID)),
        textAlign = textAlign,
        maxLines = line,
        color = color,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}