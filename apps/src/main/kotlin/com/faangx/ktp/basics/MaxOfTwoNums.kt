package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.LIGHT_GREEN
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import ksp.MiniApp

fun MaxOfTwoNumsMiniApp(
    maxOf: (Int, Int) -> Int
) {
    MiniApp(
        title = "Max of two Nums",
        composable = {
            MaxOfTwoNumsApp(maxOf)
        }
    )
}

@MiniApp("Max of two Nums")
@Composable
fun MaxOfTwoNumsApp(
    maxOf: (Int, Int) -> Int
) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }

    val max = derivedStateOf {
        val x = num1.toIntOrNull()
        val y = num2.toIntOrNull()
        if (x != null && y != null) maxOf(x, y) else null
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Maximum among",
            style = MaterialTheme.typography.titleLarge
        )

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.width(110.dp),
                value = num1,
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                onValueChange = { if (it.length <= 5) num1 = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (num1 == max.value.toString())
                        LIGHT_GREEN else Color.Transparent,
                    unfocusedContainerColor = if (num1 == max.value.toString())
                        LIGHT_GREEN else Color.Transparent,
                )
            )

            Text(
                text = "  &  ",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                modifier = Modifier.width(110.dp),
                value = num2,
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                onValueChange = { if (it.length <= 5) num2 = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (num2 == max.value.toString())
                        LIGHT_GREEN else Color.Transparent,
                    unfocusedContainerColor = if (num2 == max.value.toString())
                        LIGHT_GREEN else Color.Transparent
                )
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "is ",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = max.value?.toString() ?: SMILE_EMOJI,
                style = MaterialTheme.typography.titleLarge
            )
        }

    }
}