package com.faangx.ktp.patterns.single

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.patterns.comp.PatternInputsRow
import com.faangx.ktp.patterns.comp.PrintedPatternBox
import com.faangx.ktp.patterns.getPatterns
import com.faangx.ktp.util.captureStdOutput

@Composable
fun PatternLinesBasedApp(
    patternCode: String,
    printPattern: (lines: Int) -> Unit
) {
    PatternApp(
        patternCode
    ) { lines: Int, _: String ->
        printPattern(lines)
    }
}

@Composable
fun PatternLinesAndCharBasedApp(
    patternCode: String,
    printPattern: (lines: Int, char: Char) -> Unit
) {
    val pattern = remember {
        getPatterns().find { it.code == patternCode } ?: error("Invalid patternCode: $patternCode")
    }

    PatternApp(
        patternCode
    ) { lines: Int, customization: String ->
        printPattern(
            lines,
            customization.firstOrNull() ?: pattern.defaultCustomization!!.first()
        )
    }
}

@Composable
fun PatternLinesAndTwoCharsBasedApp(
    patternCode: String,
    printPattern: (lines: Int, char1: Char, char2: Char) -> Unit
) {
    val pattern = remember {
        getPatterns().find { it.code == patternCode } ?: error("Invalid patternCode: $patternCode")
    }

    PatternApp(
        patternCode
    ) { lines: Int, customization: String ->
        printPattern(
            lines,
            customization.firstOrNull() ?: pattern.defaultCustomization!!.first(),
            customization.getOrNull(1) ?: pattern.defaultCustomization!![1]
        )
    }
}

@Composable
fun PatternWordBasedApp(
    patternCode: String,
    printPattern: (word: String) -> Unit
) {
    PatternApp(
        patternCode
    ) { _: Int, customization: String ->
        printPattern(customization)
    }
}

@Composable
fun PatternApp(
    patternCode: String,
    printPattern: (lines: Int, customization: String) -> Unit
) {
    val pattern = remember {
        getPatterns().find { it.code == patternCode } ?: error("Invalid patternCode: $patternCode")
    }

    val lines = remember { mutableStateOf("${pattern.sample.split("\n").size}") }
    val customization = remember { mutableStateOf(pattern.defaultCustomization ?: "") }
    var printedPattern by remember { mutableStateOf("") }

    LaunchedEffect(lines.value, customization.value) {
        printedPattern = captureStdOutput {
            printPattern(
                lines.value.toIntOrNull() ?: return@captureStdOutput,
                customization.value.ifBlank {
                    pattern.defaultCustomization
                } ?: ""
            )
        }
    }

    Column(
        Modifier.fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PatternInputsRow(pattern, lines, customization)

        PrintedPatternBox(printedPattern)
    }
}