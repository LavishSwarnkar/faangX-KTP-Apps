package com.faangx.ktp.comp

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.comp.RowOrColumnScope.Col
import com.faangx.ktp.comp.RowOrColumnScope.Row

sealed class RowOrColumnScope {
    class Row(val scope: RowScope): RowOrColumnScope()
    class Col(val scope: ColumnScope): RowOrColumnScope()
}

fun Modifier.centerAlign(scope: RowOrColumnScope): Modifier {
    return when (scope) {
        is Col -> scope.scope.run { align(Alignment.CenterHorizontally) }
        is Row -> scope.scope.run { align(Alignment.CenterVertically) }
    }
}

@Composable
fun DynamicRowColumn(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable RowOrColumnScope.() -> Unit
) {

    BoxWithConstraints {

        if (maxWidth > 600.dp) {

            Row (
                modifier,
                horizontalArrangement,
                verticalAlignment,
            ) {
                Row(this).content()
            }
        } else {

            Column (
                modifier,
                verticalArrangement,
                horizontalAlignment
            ) {
                Col(this).content()
            }
        }
    }
}