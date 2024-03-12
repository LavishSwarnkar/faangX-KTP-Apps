package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SquareOfNumApp

@Composable
fun SquareOfNumAppDemo() {
    SquareOfNumApp { it * it }
}