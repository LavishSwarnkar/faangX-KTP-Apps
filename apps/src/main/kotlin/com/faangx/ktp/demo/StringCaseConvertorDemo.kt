package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.StringCaseConvertor
import com.faangx.ktp.basics.StringCaseConvertorMiniApp

fun main() {
    StringCaseConvertorMiniApp(
        ::convertToSentenceCase,
        ::convertToTitleCase,
        ::convertToUppercase,
        ::convertToLowercase,
        ::convertToSnakeCase,
    )
}

fun convertToSentenceCase(text: String): String {
    var capitalize = true
    var requireSpace = false
    return buildString {
        text.forEach { c ->
            if (requireSpace) {
                if (c != ' ') append(' ')
                requireSpace = false
            }
            append(
                if (capitalize) c.uppercaseChar() else c
            )
            if (c in listOf('.', '!', '?')) {
                capitalize = true; requireSpace = true
            } else {
                capitalize = (capitalize && c == ' ')
            }
        }
    }
}

fun convertToTitleCase(text: String): String {
    var capitalize = true
    return buildString {
        for (c in text) {
            if (c != ' ') {
                append(
                    if (capitalize) convertToUppercase(c) else c
                )
            }
            capitalize = c in listOf('.', '!', '?', ':', ';', '-', ' ', ',')
        }
    }
}

fun convertToUppercase(text: String): String {
    return buildString {
        for (c in text) {
            append(convertToUppercase(c))
        }
    }
}

fun convertToLowercase(text: String): String {
    return buildString {
        for (c in text) {
            append(convertToLowercase(c))
        }
    }
}

fun convertToSnakeCase(text: String): String {
    return buildString {
        for (c in text) {
            append(
                if (c == ' ') '_' else c
            )
        }
    }
}

fun convertToUppercase(char: Char): Char {
    return if (char in 'a'..'z') {
        Char(char.code - 32)
    } else {
        char
    }
}

@Composable
fun StringCaseConvertorDemo() {
    StringCaseConvertor(
        ::convertToSentenceCase,
        ::convertToTitleCase,
        ::convertToUppercase,
        ::convertToLowercase,
        ::convertToSnakeCase
    )
}