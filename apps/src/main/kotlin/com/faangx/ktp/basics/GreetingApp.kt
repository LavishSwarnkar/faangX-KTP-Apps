package com.faangx.ktp.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.SMILE_EMOJI
import ksp.MiniApp

@MiniApp(
    name = "Greeting App",
    repoPath = "ProgrammingFundamentals/Ep3/GreetingApp",
    paramNames = "text"
)
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
            textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
        )

        Text(
            text = greeting.value,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}