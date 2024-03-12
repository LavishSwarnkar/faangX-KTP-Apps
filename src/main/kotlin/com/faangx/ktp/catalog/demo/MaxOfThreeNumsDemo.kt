package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MaxOfThreeNumsApp

@Composable
fun MaxOfThreeNumsDemo() {
    MaxOfThreeNumsApp { x, y, z ->
        if (x > y && x > z) {
            x
        } else if (y > x && y > z) {
            y
        } else {
            z
        }
    }
}