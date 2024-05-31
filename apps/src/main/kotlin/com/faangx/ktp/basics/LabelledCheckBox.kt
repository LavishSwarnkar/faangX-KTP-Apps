package com.faangx.ktp.basics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun LabelledCheckBox(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean>,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    enabled: Boolean = true,
    onCheckChanged: (Boolean) -> Unit = {},
    color: Color = Color.Unspecified
) {
    LabelledCheckBox(
        modifier = modifier,
        checked = state.value,
        onToggle = { state.value = it; onCheckChanged(it) },
        label = label,
        enabled = enabled,
        labelStyle = labelStyle,
        color = color
    )
}

@Composable
fun LabelledCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
    label: String,
    enabled: Boolean = true,
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = Color.Unspecified
) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .run {
                if (enabled) clickable { onToggle(!checked) } else this
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(20.dp),
            checked = checked,
            onCheckedChange = { onToggle(!checked) },
            enabled = enabled
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            modifier = Modifier.alpha(
                if (enabled) 1f else ContentAlpha.disabled
            ),
            text = label,
            style = labelStyle,
            color = color
        )
    }
}