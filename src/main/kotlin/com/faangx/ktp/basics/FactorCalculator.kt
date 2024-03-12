package com.faangx.ktp.basics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.comp.HighlightedText
import com.streamliners.compose.comp.select.LabelledCheckBox

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FactorCalculator(
    getFactorsOf: (Int) -> List<Int>,
    isPrime: (Int) -> Boolean
) {

    var num by remember { mutableStateOf("") }
    val factors = remember { mutableStateListOf<Int>() }
    var prime by remember { mutableStateOf(false) }

    LaunchedEffect(num) {
        factors.clear()
        prime = false
        val x = num.toIntOrNull() ?: return@LaunchedEffect
        factors.addAll(getFactorsOf(x))
        prime = isPrime(x)
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier.fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Factors of",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    modifier = Modifier.width(120.dp),
                    value = num,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    onValueChange = { if (it.length <= 4) num = it }
                )

                Text(
                    text = "are",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }

            FlowRow(
                verticalArrangement = Arrangement.Center
            ) {
                factors.forEach { factor ->
                    HighlightedText(
                        text = factor.toString(),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            AnimatedVisibility(prime) {
                LabelledCheckBox(
                    label = "Prime number",
                    checked = true,
                    onToggle = {},
                    labelStyle = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}