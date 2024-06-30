package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.Progression.AP
import com.faangx.ktp.basics.Progression.GP
import com.faangx.ktp.comp.DynamicRowColumn
import ksp.MiniApp

private enum class Progression { AP, GP }

@MiniApp(
    name = "Progression Checker",
    repoPath = "ProgrammingFundamentals/Ep5/ProgressionChecker",
    paramNames = "series; series"
)
@Composable
fun ProgressionChecker(
    checkForAP: (List<Int>) -> String,
    checkForGP: (List<Int>) -> String
) {

    var seriesStr by remember { mutableStateOf("") }
    val type = remember { mutableStateOf<Progression?>(null) }
    var output by remember { mutableStateOf("") }

    LaunchedEffect(type.value, seriesStr) {
        type.value?.let { progressionType ->
            val series = seriesStr.split(",")
                .mapNotNull { it.trim().ifBlank { null }?.toIntOrNull() }
            output = when (progressionType) {
                AP -> checkForAP(series)
                GP -> checkForGP(series)
            }
        }
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
                    text = "Series",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    modifier = Modifier.width(120.dp),
                    value = seriesStr,
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    onValueChange = { if (it.length <= 4) seriesStr = it }
                )

                Text(
                    text = output,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }

            RadioGroup(
                state = type,
                options = Progression.entries.toList(),
                labelExtractor = { it.name }
            )
        }
    }
}