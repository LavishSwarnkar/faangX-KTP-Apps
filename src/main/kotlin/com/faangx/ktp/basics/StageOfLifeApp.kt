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

fun stageOfLifeApp(
    stageOfLife: (Int) -> String
) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "VoteAgeCheck"
    ) {
        MaterialTheme {
            Content(stageOfLife)
        }
    }
}

@Composable
private fun Content(
    stageOfLife: (Int) -> String
) {
    var age by remember { mutableStateOf("") }
    val result = derivedStateOf {
        age.toIntOrNull()?.run(stageOfLife) ?: SMILE_EMOJI
    }

    Row (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Person with age  ",
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            value = age,
            onValueChange = { if (it.length <= 5) age = it },
            textStyle = MaterialTheme.typography.h5
        )

        Text(
            text = "  years is in",
            style = MaterialTheme.typography.h5
        )

        Text(
            text = " ${result.value} ",
            style = MaterialTheme.typography.h4
        )
    }
}

fun stageOfLife(age: Int): String {
    return when {
        age >= 60 -> "Old age"
        age >= 18 -> "Adulthood"
        age >= 12 -> "Teenage"
        age >= 3 -> "Childhood"
        else -> "Infancy"
    }
}

fun main() {
    stageOfLifeApp(::stageOfLife)
}