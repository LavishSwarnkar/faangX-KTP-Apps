package com.faangx.ktp.patterns.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.faangx.ktp.patterns.Pattern

@Composable
fun PatternCard(
    pattern: Pattern,
    selectedPattern: MutableState<Pattern>,
    customization: MutableState<String>
) {
    fun onClick() {
        selectedPattern.value = pattern
        val newCustomization = pattern.defaultCustomization ?: ""
        if (customization.value.length != newCustomization.length) {
            customization.value = newCustomization
        }
    }

    Column(
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedPattern.value == pattern,
                onClick = ::onClick
            )

            Text(pattern.code)
        }

        Text(
            pattern.sample,
            modifier = Modifier.padding(horizontal = 8.dp),
            fontFamily = FontFamily.Monospace
        )
    }
}