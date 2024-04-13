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

fun StringCaseConvertorMiniApp(
    convertToSentenceCase: (String) -> String,
    convertToTitleCase: (String) -> String,
    convertToUppercase: (String) -> String,
    convertToLowercase: (String) -> String,
    convertToSnakeCase: (String) -> String
) {
    MiniApp(
        title = "String Case Convertor",
        composable = {
            StringCaseConvertor(
                convertToSentenceCase, convertToTitleCase, convertToUppercase, convertToLowercase, convertToSnakeCase
            )
        }
    )
}

data class StringCaseConvertorResult(
    val sentenceCase: String,
    val titleCase: String,
    val uppercase: String,
    val lowercase: String,
    val snakeCase: String
)

@Composable
fun StringCaseConvertor(
    convertToSentenceCase: (String) -> String,
    convertToTitleCase: (String) -> String,
    convertToUppercase: (String) -> String,
    convertToLowercase: (String) -> String,
    convertToSnakeCase: (String) -> String
) {
    val text = remember { mutableStateOf("") }

    val result = remember { mutableStateOf<StringCaseConvertorResult?>(null) }

    LaunchedEffect(text.value) {
        result.value = if (text.value.isBlank()) {
            null
        } else {
            StringCaseConvertorResult(
                sentenceCase = convertToSentenceCase(text.value),
                titleCase = convertToTitleCase(text.value),
                uppercase = convertToUppercase(text.value),
                lowercase = convertToLowercase(text.value),
                snakeCase = convertToSnakeCase(text.value)
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
                textStyle = MaterialTheme.typography.titleLarge
            )

            result.value?.let { (sentenceCase, titleCase, uppercase, lowercase, snakeCase) ->
                StringCaseConvertorResultElement("SentenceCase", sentenceCase)
                StringCaseConvertorResultElement("TitleCase", titleCase)
                StringCaseConvertorResultElement("Uppercase", uppercase)
                StringCaseConvertorResultElement("Lowercase", lowercase)
                StringCaseConvertorResultElement("SnakeCase", snakeCase)
            }
        }
    }
}

@Composable
private fun StringCaseConvertorResultElement(
    label: String,
    value: String
) {
    OutlinedTextField(
        modifier = Modifier.width(500.dp),
        label = { Text(label) },
        value = value,
        onValueChange = { },
        textStyle = MaterialTheme.typography.titleLarge,
        readOnly = true
    )
}