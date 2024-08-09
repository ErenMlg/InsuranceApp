package com.softcross.insuranceapp.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CreditCardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        val mask = "____ ____ ____ ____"
        val output = buildString {
            if (trimmed.isNotEmpty()) {
                var j = 0
                for (i in mask.indices) {
                    if (i == 4 || i == 9 || i == 14) {
                        append(" ")
                    } else if (j < trimmed.length) {
                        append(trimmed[j])
                        j++
                    } else {
                        append(mask[i])
                    }
                }
            }
        }

        val creditCardTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 8) return offset + 1
                if (offset <= 12) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }
        }

        return TransformedText(
            AnnotatedString(output),
            creditCardTranslator
        )
    }
}