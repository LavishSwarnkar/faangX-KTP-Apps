package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.LIGHT_GREEN
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.ScreenSize
import com.faangx.ktp.comp.iz
import com.faangx.ktp.comp.rememberScreenSize
import ksp.GenerateFunctionality

fun MaxOfThreeNumsMiniApp(
    maxOf: (Int, Int, Int) -> Int
) {
    MiniApp(
        title = "Max of three Nums",
        composable = {
            MaxOfThreeNumsApp(maxOf)
        }
    )
}

@GenerateFunctionality
@Composable
fun MaxOfThreeNumsApp(
    maxOf: (Int, Int, Int) -> Int
) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var num3 by remember { mutableStateOf("") }

    val max = derivedStateOf {
        val x = num1.toIntOrNull()
        val y = num2.toIntOrNull()
        val z = num3.toIntOrNull()
        if (x != null && y != null && z != null) maxOf(x, y, z) else null
    }

    val screenSize = rememberScreenSize()

    DynamicRowColumn(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Maximum among",
            style = MaterialTheme.typography.titleLarge
        )

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = num1,
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                onValueChange = { if (it.length <= 5) num1 = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (num1 == max.value.toString())
                        LIGHT_GREEN else Color.Transparent,
                    unfocusedContainerColor = if (num1 == max.value.toString())
                        LIGHT_GREEN else Color.Transparent
                )
            )

            Text(
                text = "  ,  ",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                modifier = Modifier.width(100.dp),
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

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (screenSize.iz(ScreenSize.Large)) {
                Text(
                    text = "& ",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = num3,
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                onValueChange = { if (it.length <= 5) num3 = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (num3 == max.value.toString())
                        LIGHT_GREEN else Color.Transparent,
                    unfocusedContainerColor = if (num3 == max.value.toString())
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