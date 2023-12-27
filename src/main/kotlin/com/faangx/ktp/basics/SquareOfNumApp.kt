package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.faangx.ktp.SMILE_EMOJI

fun squareOfNumApp(
    square: (Int) -> Int
) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SquareOfNum"
    ) {
        MaterialTheme {
            Content(square)
        }
    }
}

@Composable
private fun Content(
    getSquareOf: (Int) -> Int
) {
    var num by remember { mutableStateOf("") }
    val square = derivedStateOf {
        num.toIntOrNull()?.run(getSquareOf) ?: SMILE_EMOJI
    }

    Row (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Square of ",
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            value = num,
            onValueChange = { if (it.length <= 5) num = it },
            textStyle = MaterialTheme.typography.h5
        )

        Text(
            text = " is ${square.value}",
            style = MaterialTheme.typography.h5
        )
    }
}

fun main() {
    squareOfNumApp { it * it }
}