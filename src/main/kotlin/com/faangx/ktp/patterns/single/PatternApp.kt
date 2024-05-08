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
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

@Composable
@JvmName("PatternApp1")
fun PatternApp(
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
@JvmName("PatternApp2")
fun PatternApp(
    patternCode: String,
    printPattern: (lines: Int, char: Char) -> Unit
) {
    PatternApp(
        patternCode
    ) { lines: Int, customization: String ->
        printPattern(lines, customization.first())
    }
}

@Composable
@JvmName("PatternApp3")
fun PatternApp(
    patternCode: String,
    printPattern: (lines: Int, char1: Char, char2: Char) -> Unit
) {
    PatternApp(
        patternCode
    ) { lines: Int, customization: String ->
        printPattern(lines, customization.first(), customization[1])
    }
}

@Composable
@JvmName("PatternApp4")
fun PatternApp(
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