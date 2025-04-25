package com.faangx.ktp.patterns.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun PrintedPatternBox(
    pattern: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(8.dp))
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
            text = pattern,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = FontFamily.Monospace
        )
    }
}