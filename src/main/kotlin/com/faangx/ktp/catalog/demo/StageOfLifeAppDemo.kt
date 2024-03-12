package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.StageOfLifeApp

@Composable
fun StageOfLifeAppDemo() {
    StageOfLifeApp { age ->
        when {
            age >= 60 -> "Old age"
            age >= 18 -> "Adulthood"
            age >= 12 -> "Teenage"
            age >= 3 -> "Childhood"
            else -> "Infancy"
        }
    }
}