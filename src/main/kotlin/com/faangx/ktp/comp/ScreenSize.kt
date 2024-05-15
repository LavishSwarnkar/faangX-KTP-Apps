package com.faangx.ktp.comp

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ScreenSize { Small, Large }

@Composable
fun rememberScreenSize(): State<ScreenSize> {
    val screenSize = remember { mutableStateOf(ScreenSize.Small) }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val currentMaxWidth = maxWidth
        LaunchedEffect(currentMaxWidth) {
            screenSize.value = if (currentMaxWidth > 600.dp) {
                ScreenSize.Large
            } else {
                ScreenSize.Small
            }
        }
    }

    return screenSize
}

fun State<ScreenSize>.iz(size: ScreenSize): Boolean {
    return value == size
}