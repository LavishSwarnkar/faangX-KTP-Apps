package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.StageOfLifeApp

fun stageOfLife(age: Int): String {
    when {
        age >= 60 -> return "Old age"
        age >= 18 -> return "Adulthood"
        age >= 12 -> return "Teenage"
        age >= 3 -> return "Childhood"
        else -> return "Infancy"
    }
}

@Composable
fun StageOfLifeAppDemo() {
    StageOfLifeApp(::stageOfLife)
}