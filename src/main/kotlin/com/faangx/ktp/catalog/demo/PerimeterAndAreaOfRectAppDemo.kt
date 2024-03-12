package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.PerimeterAndAreaOfRectApp

@Composable
fun PerimeterAndAreaOfRectAppDemo() {
    PerimeterAndAreaOfRectApp(
        getPerimeterOfRectangle = { l, b -> 2 * (l + b) },
        getAreaOfRectangle = { l, b -> l * b }
    )
}