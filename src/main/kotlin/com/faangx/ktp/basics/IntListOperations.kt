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

data class IntListOpsResult(
    val min: Int,
    val max: Int,
    val sum: Int,
    val mean: Float
)

fun IntListOperationsMiniApp(
    findMin: (List<Int>) -> Int,
    findMax: (List<Int>) -> Int,
    calculateSum: (List<Int>) -> Int,
    calculateMean: (List<Int>) -> Float
) {
    MiniApp(
        title = "List<Int> Operations",
        composable = {
            IntListOperations(
                findMin, findMax, calculateSum, calculateMean
            )
        }
    )
}

@Composable
fun IntListOperations(
    findMin: (List<Int>) -> Int,
    findMax: (List<Int>) -> Int,
    calculateSum: (List<Int>) -> Int,
    calculateMean: (List<Int>) -> Float
) {
    val nums = remember { mutableStateOf("") }

    val result = remember { mutableStateOf<IntListOpsResult?>(null) }

    LaunchedEffect(nums.value) {
        val list = nums.value.split(",")
            .mapNotNull { it.trim().toIntOrNull() }
        result.value = if (list.isEmpty()) {
            null
        } else {
            IntListOpsResult(
                min = findMin(list),
                max = findMax(list),
                sum = calculateSum(list),
                mean = calculateMean(list)
            )
        }
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
                label = { Text("Numbers") },
                value = nums.value,
                onValueChange = { input ->
                    nums.value = input.filter { it.isDigit() || it in listOf(',', '-') }
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                )
            )

            result.value?.let { (min, max, sum, mean) ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IntListOpsResultElement("Min", min.toString())
                    IntListOpsResultElement("Max", max.toString())
                    IntListOpsResultElement("Sum", sum.toString())
                    IntListOpsResultElement("Mean", "%.2f".format(mean))
                }
            }
        }
    }
}

@Composable
private fun IntListOpsResultElement(
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label =",
            style = MaterialTheme.typography.titleLarge
        )
        HighlightedText(
            text = value
        )
    }
}