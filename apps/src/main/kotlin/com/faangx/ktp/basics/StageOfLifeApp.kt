package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import ksp.MiniApp

fun StageOfLifeMiniApp(
    stageOfLife: (Int) -> String
) {
    MiniApp(
        title = "Stage of Life",
        composable = {
            StageOfLifeApp(stageOfLife)
        }
    )
}

@MiniApp("Stage of Life")
@Composable
fun StageOfLifeApp(
    stageOfLife: (Int) -> String
) {
    var age by remember { mutableStateOf("") }
    val result = derivedStateOf {
        age.toIntOrNull()?.run(stageOfLife) ?: SMILE_EMOJI
    }

    DynamicRowColumn(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Person with age  ",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            label = { Text("Years") },
            value = age,
            onValueChange = { if (it.length <= 5) age = it },
            textStyle = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "is in",
            style = MaterialTheme.typography.titleLarge
        )

        HighlightedText(
            modifier = Modifier,
            text = " ${result.value} ",
            style = MaterialTheme.typography.titleLarge
        )
    }
}