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

fun squareOfNumApp(getSquareOf: (Int) -> Int) {
    squareOfNumAppV2 { num ->
        getSquareOf(
            num.toInt()
        ).toLong()
    }
}

fun squareOfNumAppV2(
    getSquareOf: (Long) -> Long
) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SquareOfNum"
    ) {
        MaterialTheme {
            Content(getSquareOf)
        }
    }
}

@Composable
private fun Content(
    getSquareOf: (Long) -> Long
) {
    var num by remember { mutableStateOf("") }
    val square = derivedStateOf {
        num.toLongOrNull()?.run(getSquareOf) ?: SMILE_EMOJI
    }

    Row (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Square of  ",
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            modifier = Modifier.width(130.dp),
            value = num,
            onValueChange = { if (it.length <= 6) num = it },
            textStyle = MaterialTheme.typography.h5
        )

        Text(
            text = "  is ${square.value}",
            style = MaterialTheme.typography.h5
        )
    }
}

fun main() {
    squareOfNumApp { it * it }
//    squareOfNumAppV2 { it * it }
}