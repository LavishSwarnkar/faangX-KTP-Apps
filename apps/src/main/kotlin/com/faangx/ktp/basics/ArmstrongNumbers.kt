package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.comp.HighlightedText
import com.faangx.ktp.util.captureStdOutput

fun ArmstrongNumbersMiniApp(
    printArmstrongNums: (Int) -> Unit
) {
    MiniApp(
        title = "Armstrong Checker",
        composable = {
            ArmstrongNumbers(printArmstrongNums)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArmstrongNumbers(
    printArmstrongNums: (Int) -> Unit
) {
    var n by remember { mutableStateOf("") }
    val nums = remember { mutableStateListOf<Int>() }

    LaunchedEffect(n) {
        nums.clear()
        val x = n.toIntOrNull() ?: return@LaunchedEffect
        val list = captureStdOutput { printArmstrongNums(x) }
            .split(", ")
            .dropLast(1)
            .map { it.toInt() }
        nums.addAll(list)
    }

    Column (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Armstrong numbers between 1 and",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                modifier = Modifier.width(130.dp),
                value = n,
                onValueChange = { if (it.length <= 6) n = it },
                textStyle = MaterialTheme.typography.h5
            )

            Text(
                text = "are",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }


        FlowRow(
            verticalArrangement = Arrangement.Center
        ) {
            nums.forEach { factor ->
                HighlightedText(
                    text = factor.toString(),
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}