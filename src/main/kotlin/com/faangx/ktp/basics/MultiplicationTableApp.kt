package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.util.println
import java.io.ByteArrayOutputStream

@Composable
fun MultiplicationTableApp(
    printTable: ByteArrayOutputStream.(Int, Int, Int) -> Unit
) {
    var num by remember { mutableStateOf("7") }
    var start by remember { mutableStateOf("1") }
    var end by remember { mutableStateOf("10") }

    val result = derivedStateOf {
        val numInt = num.toIntOrNull()
        val startInt = start.toIntOrNull()
        val endInt = end.toIntOrNull()

        if (numInt != null && startInt != null && endInt != null) {
            val stream = ByteArrayOutputStream()
            stream.printTable(numInt, startInt, endInt)
            stream.toString().split("\n")
        } else {
            emptyList()
        }
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        Row (
            Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Multiplication Table of  ",
                style = MaterialTheme.typography.h5
            )

            OutlinedTextField(
                modifier = Modifier.width(120.dp),
                value = num,
                onValueChange = { if (it.length <= 5) num = it },
                textStyle = MaterialTheme.typography.h5
            )

            Text(
                text = "  from  ",
                style = MaterialTheme.typography.h5
            )

            OutlinedTextField(
                modifier = Modifier.width(80.dp),
                value = start,
                onValueChange = { if (it.length <= 3) start = it },
                textStyle = MaterialTheme.typography.h5
            )

            Text(
                text = "  to  ",
                style = MaterialTheme.typography.h5
            )

            OutlinedTextField(
                modifier = Modifier.width(80.dp),
                value = end,
                onValueChange = { if (it.length <= 3) end = it },
                textStyle = MaterialTheme.typography.h5
            )

            Text(
                text = " : ",
                style = MaterialTheme.typography.h4
            )
        }

        LazyColumn {
            items(result.value) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}