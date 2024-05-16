package com.faangx.ktp.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import ksp.MiniApp

fun LeapYearCheckerMiniApp(
    isLeapYear: (Int) -> Boolean
) {
    MiniApp(
        title = "Leap Year Checker",
        composable = {
            LeapYearChecker(isLeapYear)
        }
    )
}

@MiniApp("Leap Year Checker")
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

    DynamicRowColumn(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.width(100.dp),
            label = { Text("Year") },
            value = year,
            onValueChange = { if (it.length <= 4) year = it },
            textStyle = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "is",
            style = MaterialTheme.typography.titleLarge
        )

        HighlightedText(
            text = result.value,
            style = MaterialTheme.typography.titleLarge
        )
    }
}