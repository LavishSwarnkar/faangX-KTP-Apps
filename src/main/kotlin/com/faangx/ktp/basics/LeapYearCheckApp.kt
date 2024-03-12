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

@Composable
fun LeapYearChecker(
    isLeapYear: (Int) -> Boolean
) {
    var year by remember { mutableStateOf("") }
    val result = derivedStateOf {
        val leap = year.toIntOrNull()?.run(isLeapYear)
            ?: return@derivedStateOf SMILE_EMOJI
        if (leap) "a Leap year" else "NOT a Leap year"
    }

    Row (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Year  ",
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            modifier = Modifier.width(100.dp),
            value = year,
            onValueChange = { if (it.length <= 4) year = it },
            textStyle = MaterialTheme.typography.h5
        )

        Text(
            text = "  =",
            style = MaterialTheme.typography.h5
        )

        Text(
            text = " ${result.value} ",
            style = MaterialTheme.typography.h4
        )
    }
}