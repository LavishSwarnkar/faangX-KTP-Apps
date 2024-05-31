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

@Composable
fun SquareOfNumApp(
    getSquareOf: (Int) -> Int
) {
    SquareOfNumAppV1 {
        getSquareOf(it.toInt()).toLong()
    }
}

@MiniApp(
    name = "Square Of a Number",
    repoPath = "ProgrammingFundamentals/Ep2/SquareOfNum",
    paramNames = "num"
)
@Composable
fun SquareOfNumAppV1(
    getSquareOf: (Long) -> Long
) {

    var num by remember { mutableStateOf("") }
    val square = derivedStateOf {
        num.toLongOrNull()?.run(getSquareOf) ?: SMILE_EMOJI
    }

    DynamicRowColumn(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Square of",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            modifier = Modifier.width(130.dp),
            value = num,
            onValueChange = { if (it.length <= 6) num = it },
            textStyle = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = "is",
            style = MaterialTheme.typography.titleLarge
        )

        HighlightedText(
            text = "${square.value}",
            style = MaterialTheme.typography.titleLarge
        )
    }

}