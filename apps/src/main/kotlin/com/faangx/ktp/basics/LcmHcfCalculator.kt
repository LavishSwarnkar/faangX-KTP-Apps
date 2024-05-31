package com.faangx.ktp.basics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.HighlightedText
import ksp.MiniApp

@MiniApp(
    "LCM HCF Calculator",
    "ProgrammingFundamentals/Ep4/LCM-HCF",
    "x, y; x, y"
)
@Composable
fun LcmHcfCalculator(
    getLCM: (Int, Int) -> Int,
    getHCF: (Int, Int) -> Int
) {
    var x by remember { mutableStateOf("") }
    var y by remember { mutableStateOf("") }

    var lcm by remember { mutableStateOf<Int?>(null) }
    var hcf by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(x, y) {
        lcm = null; hcf = null
        val a = x.toIntOrNull() ?: return@LaunchedEffect
        val b = y.toIntOrNull() ?: return@LaunchedEffect

        lcm = getLCM(a, b); hcf = getHCF(a, b)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier.widthIn(max = 400.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = x,
                onValueChange = { x = it },
                label = { Text("X") },
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f).padding(start = 12.dp),
                value = y,
                onValueChange = { y = it },
                label = { Text("Y") },
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LCM = ",
                style = MaterialTheme.typography.titleLarge
            )

            HighlightedText(
                modifier = Modifier,
                text = lcm?.toString() ?: SMILE_EMOJI,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "HCF = ",
                style = MaterialTheme.typography.titleLarge
            )

            HighlightedText(
                modifier = Modifier,
                text = hcf?.toString() ?: SMILE_EMOJI,
                style = MaterialTheme.typography.titleLarge
            )
        }

        AnimatedVisibility(hcf == 1) {
            LabelledCheckBox(
                label = "Co-prime numbers",
                checked = true,
                onToggle = {},
                labelStyle = MaterialTheme.typography.titleLarge
            )
        }
    }
}