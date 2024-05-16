package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.DynamicRowColumn
import ksp.GenerateFunctionality

fun PerimeterAndAreaOfRectMiniApp(
    getPerimeterOfRectangle: (Int, Int) -> Int,
    getAreaOfRectangle: (Int, Int) -> Int
) {
    MiniApp(
        title = "Perimeter & Area of Rectangle",
        composable = {
            PerimeterAndAreaOfRectApp(
                getPerimeterOfRectangle, getAreaOfRectangle
            )
        }
    )
}

@GenerateFunctionality
@Composable
fun PerimeterAndAreaOfRectApp(
    getPerimeterOfRectangle: (Int, Int) -> Int,
    getAreaOfRectangle: (Int, Int) -> Int
) {
    var length by remember { mutableStateOf("") }
    var breadth by remember { mutableStateOf("") }

    val perimeter = derivedStateOf {
        val l = length.toIntOrNull()
        val b = breadth.toIntOrNull()
        if (l != null && b != null) getPerimeterOfRectangle(l, b) else SMILE_EMOJI
    }

    val area = derivedStateOf {
        val l = length.toIntOrNull()
        val b = breadth.toIntOrNull()
        if (l != null && b != null) getAreaOfRectangle(l, b) else SMILE_EMOJI
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Rectangle with",
            style = MaterialTheme.typography.titleLarge
        )

        DynamicRowColumn(
            Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "length = ",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = length,
                    label = { Text("units") },
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    onValueChange = { if (it.length <= 4) length = it }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "breadth = ",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = breadth,
                    label = { Text("units") },
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    onValueChange = { if (it.length <= 4) breadth = it }
                )
            }
        }

        DynamicRowColumn(
            Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "has Perimeter = ${perimeter.value} units &",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Area = ${area.value} sq. units",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}