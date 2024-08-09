package com.softcross.insuranceapp.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CreditCardDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =if (text.text.length >= 5) text.text.substring(0..4) else text.text
        val mask = "__/__"
        val output = buildString {
            if (trimmed.isNotEmpty()) {
                var j = 0
                for (i in mask.indices) {
                    if (i == 2) {
                        append("/")
                    } else if (j < trimmed.length) {
                        append(trimmed[j])
                        j++
                    } else {
                        append(mask[i])
                    }
                }
            }
        }

        val cardDateTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 4) return offset + 1
                return 5
            }override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                return 4
            }
        }

        return TransformedText(
            AnnotatedString(output),
            cardDateTranslator
        )
    }
}