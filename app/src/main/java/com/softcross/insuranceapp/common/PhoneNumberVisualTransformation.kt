package com.softcross.insuranceapp.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
        val mask = "____ ___ __ __"
        val output = buildString {
            if (trimmed.isNotEmpty()) {
                var j = 0
                for (i in mask.indices) {
                    if (i == 4 || i == 8 || i == 11) {
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

        val phoneNumberTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 6) return offset + 1
                if (offset <= 8) return offset + 2
                if (offset <= 10) return offset + 3
                if (offset <= 14) return offset + 3
                return 11
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset - 1
                if (offset <= 11) return offset - 2
                return 11
            }
        }

        return TransformedText(
            AnnotatedString(output),
            phoneNumberTranslator
        )
    }
}