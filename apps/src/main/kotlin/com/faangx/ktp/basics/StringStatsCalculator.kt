package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.comp.HighlightedText

data class StringStatsCalculatorResult(
    val noOfChars: Int,

    val noOfAlphabets: Int,
    val noOfVowels: Int,
    val noOfConsonants: Int,
    val noOfDigits: Int,
    val noOfSpecialChars: Int,
    val noOfSpaces: Int,

    val noOfWords: Int,
    val noOfSentences: Int
)

fun StringStatsCalculatorMiniApp(
    calcNoOfChars: (String) -> Int,

    calcNoOfAlphabets: (String) -> Int,
    calcNoOfVowels: (String) -> Int,
    calcNoOfConsonants: (String) -> Int,
    calcNoOfDigits: (String) -> Int,
    calcNoOfSpecialChars: (String) -> Int,
    calcNoOfSpaces: (String) -> Int,

    calcNoOfWords: (String) -> Int,
    calcNoOfSentences: (String) -> Int
) {
    MiniApp(
        title = "String Stats Calculator",
        composable = {
            StringStatsCalculator(
                calcNoOfChars, calcNoOfAlphabets, calcNoOfVowels, calcNoOfConsonants, calcNoOfDigits, calcNoOfSpecialChars, calcNoOfSpaces, calcNoOfWords, calcNoOfSentences
            )
        }
    )
}

@Composable
fun StringStatsCalculator(
    calcNoOfChars: (String) -> Int,

    calcNoOfAlphabets: (String) -> Int,
    calcNoOfVowels: (String) -> Int,
    calcNoOfConsonants: (String) -> Int,
    calcNoOfDigits: (String) -> Int,
    calcNoOfSpecialChars: (String) -> Int,
    calcNoOfSpaces: (String) -> Int,

    calcNoOfWords: (String) -> Int,
    calcNoOfSentences: (String) -> Int
) {
    val text = remember { mutableStateOf("") }

    val result = remember { mutableStateOf<StringStatsCalculatorResult?>(null) }

    LaunchedEffect(text.value) {
        result.value = if (text.value.isBlank()) {
            null
        } else {
            StringStatsCalculatorResult(
                noOfChars = calcNoOfChars(text.value),

                noOfAlphabets = calcNoOfAlphabets(text.value),
                noOfVowels = calcNoOfVowels(text.value),
                noOfConsonants = calcNoOfConsonants(text.value),
                noOfDigits = calcNoOfDigits(text.value),
                noOfSpecialChars = calcNoOfSpecialChars(text.value),
                noOfSpaces = calcNoOfSpaces(text.value),

                noOfWords = calcNoOfWords(text.value),
                noOfSentences = calcNoOfSentences(text.value)
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier.width(500.dp),
                label = { Text("Text") },
                value = text.value,
                onValueChange = { input -> text.value = input },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                ),
                maxLines = 12
            )

            result.value?.let { res ->
                res.run {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StringStatsCalculatorElement("Words", noOfWords.toString())
                        StringStatsCalculatorElement(
                            "Chars",
                            noOfChars.toString(),
                            """
                                $noOfAlphabets Alphabets
                                ($noOfVowels Vowels, $noOfConsonants Consonants)
                                $noOfDigits Digits
                                $noOfSpecialChars Special Chars
                                $noOfSpaces Spaces
                            """.trimIndent()
                        )
                        StringStatsCalculatorElement("Sentences", noOfSentences.toString())
                    }
                }
            }
        }
    }
}

@Composable
private fun StringStatsCalculatorElement(
    label: String,
    value: String,
    subtitle: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label =",
                style = MaterialTheme.typography.titleLarge
            )
            HighlightedText(
                text = value
            )
        }

        subtitle?.let {
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}