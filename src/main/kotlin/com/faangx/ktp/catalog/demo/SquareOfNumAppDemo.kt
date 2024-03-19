package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SquareOfNumApp
import com.faangx.ktp.basics.SquareOfNumAppV1

@Composable
fun SquareOfNumAppDemo() {
    SquareOfNumApp { it * it }
}

@Composable
fun SquareOfNumAppV1Demo() {
    SquareOfNumAppV1 { it * it }
}