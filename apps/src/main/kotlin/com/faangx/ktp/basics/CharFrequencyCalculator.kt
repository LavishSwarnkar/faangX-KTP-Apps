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

fun CharFrequencyCalculatorMiniApp(
    calcCharFrequency: (String) -> Map<Char, Int>
) {
    MiniApp(
        title = "Char Frequency Calculator",
        composable = {
            CharFrequencyCalculator(
                calcCharFrequency
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharFrequencyCalculator(
    calcCharFrequency: (String) -> Map<Char, Int>
) {
    val text = remember { mutableStateOf("") }

    val result = remember { mutableStateOf(mapOf<Char, Int>()) }

    LaunchedEffect(text.value) {
        result.value = calcCharFrequency(text.value)
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

            FlowRow {
                result.value.forEach { (char, f) ->
                    CharFrequencyElement(char, f)
                }
            }
        }
    }
}

@Composable
private fun CharFrequencyElement(
    char: Char,
    f: Int
) {
    Column (
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$char",
            style = MaterialTheme.typography.titleLarge
        )
        HighlightedText(
            modifier = Modifier,
            text = f.toString()
        )
    }
}