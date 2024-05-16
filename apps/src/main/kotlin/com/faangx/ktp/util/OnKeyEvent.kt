package com.faangx.ktp.util

import androidx.compose.ui.input.key.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun onKeyEvent(
    event: KeyEvent,
    keyFlow: MutableSharedFlow<Char>,
    filter: (keyCode: Int) -> Boolean = {
        it in 'A'.code..'Z'.code || it in '0'.code..'9'.code
    },
    supportNumKeys: Boolean
): Boolean {
    val keyCode = event.key.nativeKeyCode
    return when {
        event.type == KeyEventType.KeyDown -> false
        filter(keyCode) -> {
            CoroutineScope(Dispatchers.IO).launch {
                keyFlow.emit(
                    Char(
                        if (supportNumKeys && keyCode in '`'.code..'i'.code) {
                            keyCode - '0'.code
                        } else {
                            keyCode
                        }
                    )
                )
            }
            true
        }
        else -> false
    }
}