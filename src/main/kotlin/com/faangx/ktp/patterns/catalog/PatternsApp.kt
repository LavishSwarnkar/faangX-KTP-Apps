package com.faangx.ktp.patterns.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.patterns.comp.PatternCard
import com.faangx.ktp.patterns.comp.PatternInputsRow
import com.faangx.ktp.patterns.comp.PrintedPatternBox
import com.faangx.ktp.patterns.getPatterns
import com.faangx.ktp.util.captureStdOutput
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

typealias PrintPatternFunctionality = (
    patternCode: String,
    lines: Int,
    customization: String
) -> Unit

fun PatternsMiniApp(
    printPattern: PrintPatternFunctionality
) {
    MiniApp(
        title = "Patterns",
        composable = {
            PatternsApp(printPattern)
        }
    )
}

@Composable
fun PatternsApp(
    printPattern: PrintPatternFunctionality
) {
    val patterns = remember { getPatterns() }

    val selectedPattern = remember { mutableStateOf(patterns.first()) }
    val lines = remember { mutableStateOf("5") }
    val customization = remember { mutableStateOf(patterns.first().defaultCustomization ?: "") }
    var pattern by remember { mutableStateOf("") }

    LaunchedEffect(selectedPattern.value, lines.value, customization.value) {
        pattern = captureStdOutput {
            printPattern(
                selectedPattern.value.code,
                lines.value.toIntOrNull() ?: return@captureStdOutput,
                customization.value.ifBlank {
                    selectedPattern.value.defaultCustomization
                } ?: ""
            )
        }
    }

    MaterialTheme {
        Column(
            Modifier.fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                Modifier.wrapContentWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(patterns) { index, pattern ->
                    PatternCard(
                        pattern = pattern,
                        selectedPattern = selectedPattern,
                        customization = customization
                    )
                }
            }
            Spacer(Modifier.size(30.dp))

            PatternInputsRow(selectedPattern.value, lines, customization)
            Spacer(Modifier.size(30.dp))

            PrintedPatternBox(pattern)
        }
    }
}