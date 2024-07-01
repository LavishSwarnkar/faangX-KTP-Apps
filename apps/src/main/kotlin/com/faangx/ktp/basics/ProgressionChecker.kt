package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.Progression.AP
import com.faangx.ktp.basics.Progression.GP
import com.faangx.ktp.comp.*
import com.faangx.ktp.comp.ScreenSize.Small
import com.faangx.ktp.test.ProgressionCheckerTest
import com.faangx.ktp.test.ProgressionCheckerTest.apExamples
import com.faangx.ktp.test.ProgressionCheckerTest.gpExamples
import com.faangx.ktp.test.ProgressionCheckerTest.randomExample
import ksp.MiniApp

private enum class Progression { AP, GP }

@OptIn(ExperimentalComposeUiApi::class)
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Check",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    RadioGroup(
                        state = type,
                        options = Progression.entries.toList(),
                        labelExtractor = { it.name },
                        layout = Layout.Row
                    )
                }

                val screenSize = rememberScreenSize()

                var showGenerateButton by remember { mutableStateOf(screenSize.iz(Small)) }

                var apExIndex by remember { mutableStateOf(0) }
                var gpExIndex by remember { mutableStateOf(0) }
                var isRandomTurn by remember { mutableStateOf(false) }

                OutlinedTextField(
                    modifier = Modifier.width(500.dp)
                        .onPointerEvent(PointerEventType.Enter) { showGenerateButton = true }
                        .onPointerEvent(PointerEventType.Exit) {
                            showGenerateButton = screenSize.iz(Small)
                        },
                    value = seriesStr,
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    onValueChange = { seriesStr = it },
                    trailingIcon = {
                        GenerateButton(
                            visible = showGenerateButton,
                            onClick = {
                                seriesStr = if (isRandomTurn) {
                                    isRandomTurn = false
                                    randomExample()
                                } else {
                                    isRandomTurn = true
                                    when (type.value ?: AP) {
                                        AP -> {
                                            apExIndex = (apExIndex + 1) % apExamples().size
                                            apExamples()[apExIndex]
                                        }
                                        GP -> {
                                            gpExIndex = (gpExIndex + 1) % gpExamples().size
                                            gpExamples()[gpExIndex]
                                        }
                                    }
                                }
                            }
                        )
                    }
                )

                if (output.isNotBlank()) {
                    LabelledCheckBox(
                        label = output,
                        checked = output.contains("with"),
                        onToggle = {},
                        labelStyle = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@MiniApp(
    name = "Progression Checker V1",
    repoPath = "ProgrammingFundamentals/Ep5/ProgressionCheckerV1",
    paramNames = "input; input"
)
@Composable
fun ProgressionCheckerV1(
    checkForAP: (String) -> String,
    checkForGP: (String) -> String
) {
   ProgressionChecker(
       checkForAP = { list: List<Int> ->
           checkForAP(list.joinToString(","))
       },
       checkForGP = { list: List<Int> ->
           checkForGP(list.joinToString(","))
       }
   )
}