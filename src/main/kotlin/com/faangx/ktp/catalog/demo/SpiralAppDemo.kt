package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import com.faangx.ktp.basics.loops.Spiral
import com.faangx.ktp.basics.loops.SpiralConfig

@Composable
fun SpiralAppDemo() {
    val basic = SpiralConfig(
        radii = listOf(1) + (1..70).toList(),
        centerOffset = Offset(-100f, -100f)
    )

    val goldenRatio = SpiralConfig(
        radii = listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597),
        centerOffset = Offset(-100f, -100f)
    )

//    Spiral(basic)
    Spiral(goldenRatio)
}