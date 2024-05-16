package com.faangx.ktp.patterns.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.patterns.Pattern

@Composable
fun PatternInputsRow(
    pattern: Pattern,
    lines: MutableState<String>,
    customization: MutableState<String>
) {
    Row(
        Modifier.width(300.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        if (pattern.type.requiresLinesInput()) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                label = { Text("Lines") },
                value = lines.value,
                onValueChange = {
                    lines.value = it
                }
            )
        }

        if (pattern.type.requiresCustomization()) {
            OutlinedTextField(
                modifier = Modifier.weight(2f),
                label = { Text(pattern.type.customizationLabel()) },
                value = customization.value,
                onValueChange = {
                    customization.value = it
                }
            )
        }
    }
}