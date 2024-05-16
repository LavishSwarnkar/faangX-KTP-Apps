package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import ksp.MiniApp

fun SimpleInterestCalculatorMiniAppV1(
    calculateInterest: (
        principal: Float,
        rate: Float,
        time: Float
    ) -> Float
) {
    MiniApp(
        title = "Simple Interest Calculator",
        composable = {
            SimpleInterestCalculatorV1(
                calculateInterest
            )
        }
    )
}

@MiniApp(
    "Simple Interest Calculator",
    "principal, rate, time"
)
@Composable
fun SimpleInterestCalculator(
    calculateInterest: (
        principal: Int,
        rate: Int,
        time: Int
    ) -> Int
) {
    val principal = remember { mutableStateOf("") }
    val rate = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val interest = remember { mutableStateOf<String?>(null) }
    val totalAmount = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(principal.value, rate.value, time.value) {
        val p = principal.value.toIntOrNull() ?: return@LaunchedEffect
        val r = rate.value.toIntOrNull() ?: return@LaunchedEffect
        val t = time.value.toIntOrNull() ?: return@LaunchedEffect

        val i = calculateInterest(p, r, t)
        val tA = p + i

        interest.value = i.toString()
        totalAmount.value = tA.toString()
    }

    Content(principal, rate, time, interest, totalAmount)
}

@Composable
fun SimpleInterestCalculatorV1(
    calculateInterest: (
        principal: Float,
        rate: Float,
        time: Float
    ) -> Float
) {
    val principal = remember { mutableStateOf("") }
    val rate = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val interest = remember { mutableStateOf<String?>(null) }
    val totalAmount = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(principal.value, rate.value, time.value) {
        val p = principal.value.toFloatOrNull() ?: return@LaunchedEffect
        val r = rate.value.toFloatOrNull() ?: return@LaunchedEffect
        val t = time.value.toFloatOrNull() ?: return@LaunchedEffect

        val i = calculateInterest(p, r, t)
        val tA = p + i

        interest.value = String.format("%.2f", i)
        totalAmount.value = String.format("%.2f", tA)
    }

    Content(principal, rate, time, interest, totalAmount)
}

@Composable
private fun Content(
    principal: MutableState<String>,
    rate: MutableState<String>,
    time: MutableState<String>,
    interest: State<String?>,
    totalAmount: State<String?>
) {
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                label = { Text("Principal amount") },
                value = principal.value,
                onValueChange = { principal.value = it },
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.size(8.dp))

            OutlinedTextField(
                label = { Text("Rate in %") },
                value = rate.value,
                onValueChange = { rate.value = it },
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.size(8.dp))

            OutlinedTextField(
                label = { Text("Time in years") },
                value = time.value,
                onValueChange = { time.value = it },
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.size(16.dp))

            Text(
                text = "Interest = ${interest.value ?: SMILE_EMOJI}",
                style = MaterialTheme.typography.h5
            )
            Spacer(Modifier.size(16.dp))

            Text(
                text = "Total amount = ${totalAmount.value ?: SMILE_EMOJI}",
                style = MaterialTheme.typography.h5
            )
        }
    }
}