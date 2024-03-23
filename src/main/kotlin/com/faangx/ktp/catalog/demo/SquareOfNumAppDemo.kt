package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SquareOfNumApp
import com.faangx.ktp.basics.SquareOfNumAppV1

fun square(x: Int) = x * x * x

fun square(x: Long) = x * x * x

@Composable
fun SquareOfNumAppDemo() {
    SquareOfNumApp(::square)
}

@Composable
fun SquareOfNumAppV1Demo() {
    SquareOfNumAppV1(::square)
}