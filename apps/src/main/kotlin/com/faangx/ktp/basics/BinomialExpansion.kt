package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniApp

@MiniApp(
    name = "Binomial Expansion MiniApp",
    repoPath = "ProgrammingFundamentals/Ep3.Ext/BinomialExpansion",
    paramNames = "n"
)
@Composable
fun BinomialExpansion(
    printBinomialExpansion: (Int) -> Unit
) {
    var n by remember { mutableStateOf("") }
    val expansion = derivedStateOf {
        n.toIntOrNull()?.run {
            replacePowers(
                captureStdOutput {
                    printBinomialExpansion(this)
                }
            )
        } ?: SMILE_EMOJI
    }

    Column (
        Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "(a + b)",
                style = MaterialTheme.typography.displayMedium
            )

            OutlinedTextField(
                modifier = Modifier.width(60.dp)
                    .padding(bottom = 24.dp),
                value = n,
                onValueChange = { if (it.length <= 2) n = it },
                textStyle = MaterialTheme.typography.titleMedium
            )
        }

        Text(
            text = "=",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = expansion.value,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )
    }
}

private fun replacePowers(input: String): String {
    val pattern = """(\w+)\^(\d+)""".toRegex()
    return pattern.replace(input) {
        val base = it.groupValues[1]
        val exponent = it.groupValues[2].toInt()
        val superscriptDigits = listOf(
            '\u2070', '\u00B9', '\u00B2', '\u00B3', '\u2074',
            '\u2075', '\u2076', '\u2077', '\u2078', '\u2079'
        )
        val superscriptExponent = exponent.toString().map {
            superscriptDigits[it.toString().toInt()]
        }.joinToString("")
        "$base$superscriptExponent"
    }
}