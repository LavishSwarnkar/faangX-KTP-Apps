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
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniApp

@MiniApp(
    "Armstrong Numbers",
    "ProgrammingFundamentals/Ep4/ArmstrongNumbers",
    "upTo"
)
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

        DynamicRowColumn(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Armstrong numbers",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "between 1 &",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    modifier = Modifier.width(130.dp)
                        .padding(start = 12.dp),
                    value = n,
                    onValueChange = { if (it.length <= 6) n = it },
                    textStyle = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                text = "are",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.size(12.dp))

        FlowRow(
            verticalArrangement = Arrangement.Center
        ) {
            nums.forEach { factor ->
                HighlightedText(
                    text = factor.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@MiniApp(
    "Armstrong Numbers V1",
    "ProgrammingFundamentals/Ep5/ArmstrongNumbersV1",
    "upTo"
)
@Composable
fun ArmstrongNumbersV1(
    getArmstrongNums: (Int) -> List<Int>
) {
    ArmstrongNumbers {
        val nums = getArmstrongNums(it)
        print(nums.joinToString(", "))
    }
}