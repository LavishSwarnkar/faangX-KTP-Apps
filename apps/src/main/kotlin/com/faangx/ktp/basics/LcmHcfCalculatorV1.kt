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
    "LCM HCF Calculator V1",
    "ProgrammingFundamentals/Ep5/LCM-HCF-V1",
    "nums; nums"
)
@Composable
fun LcmHcfCalculatorV1(
    getLCM: (List<Int>) -> Int,
    getHCF: (List<Int>) -> Int
) {
    var numsStr by remember { mutableStateOf("") }

    var lcm by remember { mutableStateOf<Int?>(null) }
    var hcf by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(numsStr) {
        lcm = null; hcf = null

        val nums = numsStr.split(",")
            .mapNotNull { it.ifBlank { null }?.trim()?.toInt() }

        if (nums.size >= 2) {
            lcm = getLCM(nums); hcf = getHCF(nums)
        }
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
                modifier = Modifier.width(500.dp),
                value = numsStr,
                onValueChange = { numsStr = it },
                label = { Text("Numbers") },
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