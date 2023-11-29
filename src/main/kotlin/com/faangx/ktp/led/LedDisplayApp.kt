package com.faangx.ktp.led

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.faangx.ktp.led.LedDisplayAppConfig.BOX_SIZE
import com.faangx.ktp.led.LedDisplayAppConfig.backgroundColor
import com.faangx.ktp.led.LedDisplayAppConfig.objectColor
import com.faangx.ktp.led.style.LedDisplayAppStyle
import com.faangx.ktp.led.style.LedDisplayAppStyle.*
import com.faangx.ktp.led.style.LedDisplayClearAndDrawStyle
import com.faangx.ktp.led.style.LedDisplayOverDrawStyle
import kotlinx.coroutines.flow.Flow
import java.awt.Toolkit

fun interface LedDisplayAppFunctionality {
    fun getCoordinateFlow(
        height: Int,
        width: Int
    ): Flow<Coordinate>
}

@Composable
private fun AppScreen(
    height: Int,
    width: Int,
    usableSize: DpSize,
    functionality: LedDisplayAppFunctionality,
    style: LedDisplayAppStyle
) {
    when (style) {
        ClearAnDraw ->
            LedDisplayClearAndDrawStyle(height, width, usableSize, functionality, style)
        DrawOver ->
            LedDisplayOverDrawStyle(height, width, usableSize, functionality)
    }
}

fun ledDisplayApp(
    functionality: LedDisplayAppFunctionality,
    style: LedDisplayAppStyle
) = application {
    val state = rememberWindowState(placement = WindowPlacement.Maximized)

    val radius = BOX_SIZE / 2

    val (height, width) = with(LocalDensity.current) {
        val size = Toolkit.getDefaultToolkit().screenSize

        val h = (size.height - 56) * density
        val w = (size.width - 28) * density
        h.toInt() to w.toInt()
    }

    val usableSize = with(LocalDensity.current) {
        DpSize(
            ((width / radius) * radius / density).dp,
            ((height / radius) * radius / density).dp
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "LedDisplayApp"
    ) {
        AppScreen(height, width, usableSize, functionality, style)
    }
}