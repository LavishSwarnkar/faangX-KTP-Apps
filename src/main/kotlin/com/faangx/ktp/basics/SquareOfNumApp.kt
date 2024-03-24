package com.faangx.ktp.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI

fun SquareOfNumMiniApp(
    getSquareOf: (Int) -> Int
) {
    MiniApp(
        title = "Square Of a Number",
        composable = {
            SquareOfNumApp(getSquareOf)
        }
    )
}

fun SquareOfNumMiniAppV1(
    getSquareOf: (Long) -> Long
) {
    MiniApp(
        title = "Square Of a Number",
        composable = {
            SquareOfNumAppV1(getSquareOf)
        }
    )
}

@Composable
fun SquareOfNumApp(
    getSquareOf: (Int) -> Int
) {
    SquareOfNumAppV1 {
        getSquareOf(it.toInt()).toLong()
    }
}

@Composable
fun SquareOfNumAppV1(
    getSquareOf: (Long) -> Long
) {
    var num by remember { mutableStateOf("") }
    val square = derivedStateOf {
        num.toLongOrNull()?.run(getSquareOf) ?: SMILE_EMOJI
    }

    Row (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Square of  ",
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            modifier = Modifier.width(130.dp),
            value = num,
            onValueChange = { if (it.length <= 6) num = it },
            textStyle = MaterialTheme.typography.h5
        )

        Text(
            text = "  is ${square.value}",
            style = MaterialTheme.typography.h5
        )
    }
}