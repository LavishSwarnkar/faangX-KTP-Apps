package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
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

fun oddEvenCheckApp(
    checkEvenOdd: (Int) -> String
) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OddEvenCheck"
    ) {
        MaterialTheme {
            Content(checkEvenOdd)
        }
    }
}

@Composable
private fun Content(
    checkEvenOdd: (Int) -> String
) {
    var num by remember { mutableStateOf("") }
    val type = derivedStateOf {
        num.toIntOrNull()?.run(checkEvenOdd) ?: SMILE_EMOJI
    }

    Row (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            value = num,
            onValueChange = { if (it.length <= 5) num = it },
            textStyle = MaterialTheme.typography.h5
        )

        Text(
            text = "  is an",
            style = MaterialTheme.typography.h5
        )

        Text(
            text = " ${type.value} ",
            style = MaterialTheme.typography.h4
        )

        Text(
            text = "number",
            style = MaterialTheme.typography.h5
        )
    }
}

fun checkOddEven(num: Int): String {
    if (num % 2 == 0) {
        return "Even"
    } else {
        return "Odd"
    }
}

// +, -, *, /, %
fun main() {
    oddEvenCheckApp(::checkOddEven)
}