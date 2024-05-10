package com.faangx.ktp.num_pad

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key.Companion.Backspace
import androidx.compose.ui.input.key.Key.Companion.Nine
import androidx.compose.ui.input.key.Key.Companion.NumPad0
import androidx.compose.ui.input.key.Key.Companion.NumPad9
import androidx.compose.ui.input.key.Key.Companion.Zero
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.faangx.ktp.util.onKeyEvent
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    application {

        val keyFlow = MutableSharedFlow<Char>()

        Window(
            onCloseRequest = ::exitApplication,
            onKeyEvent = { keyEvent ->
                onKeyEvent(
                    event = keyEvent,
                    keyFlow = keyFlow,
                    filter = {
                        it in Zero.nativeKeyCode..Nine.nativeKeyCode ||
                                it in NumPad0.nativeKeyCode..NumPad9.nativeKeyCode ||
                                it == Backspace.nativeKeyCode
                    },
                    supportNumKeys = true
                )
            },
            title = "Num Pad app"
        ) {
            Column {
                NumPad(
                    keyFlow = keyFlow
                )
            }
        }
    }
}