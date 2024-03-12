package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.faangx.ktp.SMILE_EMOJI

@Composable
fun PerimeterAndAreaOfRectApp(
    getPerimeterOfRectangle: (Int, Int) -> Int,
    getAreaOfRectangle: (Int, Int) -> Int
) {
    var length by remember { mutableStateOf("") }
    var breadth by remember { mutableStateOf("") }

    val perimeter = derivedStateOf {
        val l = length.toIntOrNull()
        val b = breadth.toIntOrNull()
        if (l != null && b != null) getPerimeterOfRectangle(l, b) else SMILE_EMOJI
    }

    val area = derivedStateOf {
        val l = length.toIntOrNull()
        val b = breadth.toIntOrNull()
        if (l != null && b != null) getAreaOfRectangle(l, b) else SMILE_EMOJI
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Rectangle with",
            style = MaterialTheme.typography.h5
        )

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "length = ",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = length,
                textStyle = MaterialTheme.typography.h5,
                onValueChange = { if (it.length <= 4) length = it }
            )

            Text(
                text = " units & breadth = ",
                style = MaterialTheme.typography.h5
            )

            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = breadth,
                textStyle = MaterialTheme.typography.h5,
                onValueChange = { if (it.length <= 4) breadth = it }
            )

            Text(
                text = " units",
                style = MaterialTheme.typography.h5
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "has Perimeter = ",
                style = MaterialTheme.typography.h5
            )

            Text(
                text = perimeter.value.toString(),
                style = MaterialTheme.typography.h4
            )

            Text(
                text = " units & area = ",
                style = MaterialTheme.typography.h5
            )

            Text(
                text = area.value.toString(),
                style = MaterialTheme.typography.h4
            )

            Text(
                text = " sq. units",
                style = MaterialTheme.typography.h5
            )
        }
    }
}