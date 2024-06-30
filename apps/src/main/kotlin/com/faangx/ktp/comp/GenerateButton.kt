package com.faangx.ktp.comp

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenerateButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier.padding(end = 12.dp),
        visible = visible,
        enter = fadeIn() + expandHorizontally(
            expandFrom = Alignment.Start
        ),
        exit = fadeOut() + shrinkHorizontally(
            shrinkTowards = Alignment.Start
        )
    ) {
        FilledIconButton(
            onClick = onClick
        ) {
            Icon(Icons.Default.Refresh, "Generate")
        }
    }
}