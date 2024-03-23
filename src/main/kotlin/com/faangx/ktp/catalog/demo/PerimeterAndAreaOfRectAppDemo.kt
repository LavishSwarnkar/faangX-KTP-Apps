package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.PerimeterAndAreaOfRectApp

fun getPerimeterOfRectangle(l: Int, b: Int): Int {
    return 2 * l + b
}

fun getAreaOfRectangle(l: Int, b: Int): Int {
    return l * b
}

@Composable
fun PerimeterAndAreaOfRectAppDemo() {
    PerimeterAndAreaOfRectApp(
        getPerimeterOfRectangle = ::getPerimeterOfRectangle,
        getAreaOfRectangle = ::getAreaOfRectangle
    )
}