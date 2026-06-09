package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.StringStatsCalculator

private const val VOWELS = "aeiou"

fun calcNoOfChars(text: String): Int = text.length

fun calcNoOfAlphabets(text: String): Int = text.count { it.isLetter() }

fun calcNoOfVowels(text: String): Int = text.count { it.lowercaseChar() in VOWELS }

fun calcNoOfConsonants(text: String): Int =
    text.count { it.isLetter() && it.lowercaseChar() !in VOWELS }

fun calcNoOfDigits(text: String): Int = text.count { it.isDigit() }

fun calcNoOfSpecialChars(text: String): Int =
    text.count { !it.isLetterOrDigit() && !it.isWhitespace() }

fun calcNoOfSpaces(text: String): Int = text.count { it.isWhitespace() }

fun calcNoOfWords(text: String): Int =
    text.split(Regex("\\s+")).count { it.isNotBlank() }

fun calcNoOfSentences(text: String): Int =
    text.count { it == '.' || it == '!' || it == '?' }

@Composable
fun StringStatsCalculatorDemo() {
    StringStatsCalculator(
        calcNoOfChars = ::calcNoOfChars,
        calcNoOfAlphabets = ::calcNoOfAlphabets,
        calcNoOfVowels = ::calcNoOfVowels,
        calcNoOfConsonants = ::calcNoOfConsonants,
        calcNoOfDigits = ::calcNoOfDigits,
        calcNoOfSpecialChars = ::calcNoOfSpecialChars,
        calcNoOfSpaces = ::calcNoOfSpaces,
        calcNoOfWords = ::calcNoOfWords,
        calcNoOfSentences = ::calcNoOfSentences
    )
}
