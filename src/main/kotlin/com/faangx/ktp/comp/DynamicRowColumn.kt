package com.faangx.ktp.comp

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DynamicRowColumn(
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit
) {
    BoxWithConstraints {

        LaunchedEffect(maxWidth) {
            println("width = $maxWidth")
        }

        if (maxWidth > 600.dp) {

            Row (
                modifier,
                horizontalArrangement,
                verticalAlignment,
            ) {
                content()
            }
        } else {

            Column (
                modifier,
                verticalArrangement,
                horizontalAlignment
            ) {
                content()
            }
        }
    }
}