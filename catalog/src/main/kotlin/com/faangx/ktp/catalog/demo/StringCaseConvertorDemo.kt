package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.StringCaseConvertor

fun convertToSentenceCase(text: String): String =
    text.lowercase().replaceFirstChar { it.uppercase() }

fun convertToTitleCase(text: String): String =
    text.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }

fun convertToUppercase(text: String): String = text.uppercase()

fun convertToLowercase(text: String): String = text.lowercase()

fun convertToSnakeCase(text: String): String =
    text.trim().lowercase().replace(Regex("\\s+"), "_")

@Composable
fun StringCaseConvertorDemo() {
    StringCaseConvertor(
        convertToSentenceCase = ::convertToSentenceCase,
        convertToTitleCase = ::convertToTitleCase,
        convertToUppercase = ::convertToUppercase,
        convertToLowercase = ::convertToLowercase,
        convertToSnakeCase = ::convertToSnakeCase
    )
}
