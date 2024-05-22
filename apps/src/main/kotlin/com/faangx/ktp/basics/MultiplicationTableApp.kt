package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniApp
import java.io.ByteArrayOutputStream

@MiniApp("Multiplication Table", "num")
@Composable
fun MultiplicationTableApp(
    printTable: (Int) -> Unit
) {
    Content(
        printTable = { num, _, _ -> printTable(num) },
        customizeEndPoints = false
    )
}

@MiniApp("Multiplication Table v1", "num, start, end")
@Composable
fun MultiplicationTableAppV1(
    printTable: (Int, Int, Int) -> Unit
) {
    Content(
        printTable = printTable,
        customizeEndPoints = true
    )
}

@Composable
private fun Content(
    printTable: (Int, Int, Int) -> Unit,
    customizeEndPoints: Boolean
) {
    var num by remember { mutableStateOf("7") }
    var start by remember { mutableStateOf("1") }
    var end by remember { mutableStateOf("10") }

    val result = derivedStateOf {
        val numInt = num.toIntOrNull()
        val startInt = start.toIntOrNull()
        val endInt = end.toIntOrNull()

        if (numInt != null && startInt != null && endInt != null) {
            captureStdOutput {
                printTable(numInt, startInt, endInt)
            }.split("\n")
        } else {
            emptyList()
        }
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        DynamicRowColumn (
            Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "Multiplication Table of",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                modifier = Modifier.width(120.dp),
                value = num,
                label = { Text("No") },
                onValueChange = { if (it.length <= 5) num = it },
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
            )

            if (customizeEndPoints) {
                Row {
                    OutlinedTextField(
                        modifier = Modifier.width(80.dp),
                        value = start,
                        label = { Text("From") },
                        onValueChange = { if (it.length <= 3) start = it },
                        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                    )

                    OutlinedTextField(
                        modifier = Modifier.padding(start = 12.dp)
                            .width(80.dp),
                        value = end,
                        label = { Text("To") },
                        onValueChange = { if (it.length <= 3) end = it },
                        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                    )
                }
            }
        }

        LazyColumn {
            items(result.value) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}