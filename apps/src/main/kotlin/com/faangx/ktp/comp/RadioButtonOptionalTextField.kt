package com.faangx.ktp.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonOptionalTextField(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    hint: String,
    input: MutableState<String>,
    showRadioButtons: MutableState<Boolean>,
    singleLine: Boolean = true
) {
    RadioButtonOptionalTextField(
        modifier,
        selected,
        onClick,
        hint,
        value = input.value,
        onValueChange = { input.value = it },
        showRadioButtons,
        singleLine
    )
}

@Composable
fun RadioButtonOptionalTextField(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    showRadioButtons: MutableState<Boolean>,
    singleLine: Boolean = true
) {
    Row (
        modifier.clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (Modifier.size(40.dp)) {
            AnimatedVisibility(showRadioButtons.value) {
                RadioButton(selected, onClick)
            }
        }

        OutlinedTextField(
            modifier = Modifier.padding(start = 8.dp).weight(1f),
            label = { Text(hint) },
            value = value,
            onValueChange = onValueChange,
            enabled = selected,
            textStyle = MaterialTheme.typography.titleLarge,
            singleLine = singleLine,
            maxLines = if (singleLine) 1 else 10
        )
    }
}