package com.faangx.ktp.basics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import com.faangx.ktp.util.captureStdOutput
import com.streamliners.compose.comp.select.LabelledCheckBox
import ksp.MiniApp

@MiniApp(
    name = "Factor Calculator",
    repoPath = "ProgrammingFundamentals/Ep3/FactorCalculator",
    paramNames = "num; num"
)
@Composable
fun FactorCalculator(
    printFactorsOf: (Int) -> Unit,
    isPrime: (Int) -> Boolean
) {
    FactorCalculatorV1(
        getFactorsOf = { num ->
            captureStdOutput { printFactorsOf(num) }
                .split(", ")
                .dropLast(1)
                .map { it.toInt() }
        },
        isPrime = isPrime
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FactorCalculatorV1(
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

            DynamicRowColumn(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Factors of",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    modifier = Modifier.width(120.dp),
                    value = num,
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    onValueChange = { if (it.length <= 4) num = it }
                )

                Text(
                    text = "are",
                    style = MaterialTheme.typography.titleLarge,
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