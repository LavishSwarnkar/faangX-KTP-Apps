package com.faangx.ktp.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MiniAppComp(
    modifier: Modifier,
    title: String,
    composable: @Composable () -> Unit
) {
    Column (
        modifier = modifier.padding(16.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(12.dp),
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        // Clip to the designated MiniApp area so demos that draw outside their
        // bounds (e.g. Spiral's scale animation) don't bleed into the rest of the UI.
        Box(
            modifier = Modifier.fillMaxSize()
                .clipToBounds()
        ) {
            composable()
        }
    }
}