package com.faangx.ktp.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.faangx.ktp.MiniApp
import ksp.MiniApp

@MiniApp(
    name = "Text App",
    repoPath = "ProgrammingFundamentals/Ep2/TextApp"
)
@Composable
fun TextApp(
    getText: () -> String
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getText(),
            style = MaterialTheme.typography.h3
        )
    }
}