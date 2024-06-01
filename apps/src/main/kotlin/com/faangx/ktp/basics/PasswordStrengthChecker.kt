package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.select.LabelledCheckBox

typealias PasswordStrengthCheckResult = Map<String, Boolean>

@Composable
fun PasswordStrengthChecker(
    checkStrength: (String) -> PasswordStrengthCheckResult
) {

    var password by remember { mutableStateOf("") }
    val result = remember { mutableStateOf(checkStrength("")) }

    LaunchedEffect(password) {
        result.value = checkStrength(password)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            textStyle = MaterialTheme.typography.titleLarge,
        )

        Column(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            result.value.entries.forEach { (check, passed) ->
                LabelledCheckBox(
                    checked = passed,
                    onToggle = { },
                    label = check
                )
            }
        }
    }

}