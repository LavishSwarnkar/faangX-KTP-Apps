package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.patterns.comp.PatternCard
import com.faangx.ktp.patterns.comp.PatternInputsRow
import com.faangx.ktp.patterns.comp.PrintedPatternBox
import com.faangx.ktp.patterns.getPatterns
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniApp

@MiniApp(
    name = "Patterns",
    repoPath = "ProgrammingFundamentals/Ep3/Patterns",
    paramNames = "patternCode, lines, customization"
)
@Composable
fun PatternsApp(
    printPattern: (String, Int, String) -> Unit
) {
    val patterns = remember { getPatterns() }

    val selectedPattern = remember { mutableStateOf(patterns.first()) }
    val lines = remember { mutableStateOf("5") }
    val customization = remember { mutableStateOf(patterns.first().defaultCustomization ?: "") }
    var pattern by remember { mutableStateOf("") }

    LaunchedEffect(selectedPattern.value, lines.value, customization.value) {
        pattern = captureStdOutput {
            val mCustomization = if (customization.value.length < selectedPattern.value.type.customizationLength()) {
                selectedPattern.value.defaultCustomization
            } else customization.value
            printPattern(
                selectedPattern.value.code,
                lines.value.toIntOrNull() ?: return@captureStdOutput,
                mCustomization ?: ""
            )
        }
    }

    MaterialTheme {
        Column(
            Modifier.fillMaxSize()
                .padding(vertical = 30.dp),
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