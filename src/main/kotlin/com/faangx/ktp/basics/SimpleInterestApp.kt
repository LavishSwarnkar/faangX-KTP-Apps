package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.faangx.ktp.SMILE_EMOJI

fun interface SimpleInterestAppFunctionality {
    fun getInterest(
        principal: Float,
        rate: Float,
        time: Float
    ): Float
}

@Composable
private fun AppScreen(functionality: SimpleInterestAppFunctionality) {
    var principal by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    
    val interest = derivedStateOf {
        calculateInterest(principal, rate, time, functionality)
    }
    val totalAmount = derivedStateOf {
        val p = principal.toFloatOrNull() ?: return@derivedStateOf null
        val i = interest.value?.toFloatOrNull() ?: return@derivedStateOf null
        p + i
    }

    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Simple Interest Calculator",
                style = MaterialTheme.typography.h4
            )
            Spacer(Modifier.size(50.dp))
            
            OutlinedTextField(
                label = { Text("Principal amount") },
                value = principal,
                onValueChange = { principal = it },
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.size(8.dp))
            
            OutlinedTextField(
                label = { Text("Rate in %") },
                value = rate,
                onValueChange = { rate = it },
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.size(8.dp))
            
            OutlinedTextField(
                label = { Text("Time in years") },
                value = time,
                onValueChange = { time = it },
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

fun calculateInterest(
    principal: String,
    rate: String,
    time: String,
    functionality: SimpleInterestAppFunctionality
): String? {
    val p = principal.toFloatOrNull() ?: return null
    val r = rate.toFloatOrNull() ?: return null
    val t = time.toFloatOrNull() ?: return null
    return String.format("%.2f", functionality.getInterest(p, r, t))
}

fun simpleInterestApp(
    functionality: SimpleInterestAppFunctionality
) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SimpleInterestCalculator"
    ) {
        AppScreen(functionality)
    }
}

fun main() {
//    simpleInterestApp { p, r, t ->
//        p * r * t / 100
//    }
    println(1 / 0)
}