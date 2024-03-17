package com.faangx.ktp.basics.string

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.faangx.ktp.comp.RadioButtonOptionalTextField
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Base64App(
    encode: (String) -> String,
    decode: (String) -> String
) {

    var encoderMode by remember { mutableStateOf(true) }

    val decoded = remember { mutableStateOf("") }
    val encoded = remember { mutableStateOf("") }

    val showRadioButtons = remember { mutableStateOf(false) }

    LaunchedEffect(decoded.value, encoded.value) {
        if (encoderMode)
            encoded.value = encode(decoded.value)
        else
            decoded.value = decode(encoded.value)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .onPointerEvent(PointerEventType.Enter) { showRadioButtons.value = true }
                .onPointerEvent(PointerEventType.Exit) { showRadioButtons.value = false },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            RadioButtonOptionalTextField(
                selected = encoderMode,
                onClick = { encoderMode = true },
                hint = "Plain text",
                input = decoded,
                showRadioButtons = showRadioButtons,
                singleLine = false
            )

            RadioButtonOptionalTextField(
                selected = !encoderMode,
                onClick = { encoderMode = false },
                hint = "Encoded text",
                input = encoded,
                showRadioButtons = showRadioButtons,
                singleLine = false
            )
        }
    }
}