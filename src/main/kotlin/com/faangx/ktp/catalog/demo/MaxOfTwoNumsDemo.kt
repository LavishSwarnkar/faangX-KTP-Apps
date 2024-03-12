package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MaxOfTwoNumsApp

@Composable
fun MaxOfTwoNumsDemo() {
    MaxOfTwoNumsApp { x, y ->
        if (x > y) x else y
    }
}