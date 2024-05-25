package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import ksp.MiniApp

@MiniApp(
    name = "Odd Even Checker",
    repoPath = "ProgrammingFundamentals/Ep2/OddEvenChecker",
    paramNames = "num"
)
@Composable
fun OddEvenChecker(
    checkEvenOdd: (Int) -> String
) {
    var num by remember { mutableStateOf("") }
    val type = derivedStateOf {
        num.toIntOrNull()?.run(checkEvenOdd) ?: SMILE_EMOJI
    }

    DynamicRowColumn(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            label = { Text("No") },
            value = num,
            onValueChange = { if (it.length <= 5) num = it },
            textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
        )

        Text(
            text = "is",
            style = MaterialTheme.typography.titleLarge
        )

        HighlightedText(
            modifier = Modifier,
            text = type.value,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}