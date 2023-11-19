package com.faangx.ktp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun interface AppFunctionality {
    fun getText(): String
}

@Composable
private fun AppScreen(functionality: AppFunctionality) {
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = functionality.getText(),
                style = MaterialTheme.typography.h1
            )
        }
    }
}

fun textApp(
    functionality: AppFunctionality
) = application {
    Window(onCloseRequest = ::exitApplication) {
        AppScreen(functionality)
    }
}