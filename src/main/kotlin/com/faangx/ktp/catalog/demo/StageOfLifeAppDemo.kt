package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.StageOfLifeApp

fun stageOfLife(age: Int): String {
    return when {
        age >= 60 -> "Old age"
        age >= 18 -> "Adulthood"
        age >= 12 -> "Teenage"
        age >= 3 -> "Childhood"
        else -> "Infancy"
    }
}

@Composable
fun StageOfLifeAppDemo() {
    StageOfLifeApp(::stageOfLife)
}