package com.faangx.ktp.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.SMILE_EMOJI

@Composable
fun GreetingApp(
    greet: (String) -> String
) {
    var name by remember { mutableStateOf("") }

    val greeting = remember {
        derivedStateOf {
            name.ifBlank { null }?.let(greet) ?: SMILE_EMOJI
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text("Name") },
            value = name,
            onValueChange = { name = it },
            textStyle = MaterialTheme.typography.h6
        )

        Text(
            text = greeting.value,
            style = MaterialTheme.typography.h3
        )
    }
}