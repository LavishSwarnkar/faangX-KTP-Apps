package com.faangx.ktp.basics.string

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
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
fun CeaserCipher(
    encode: (String, delta: Int) -> String,
    decode: (String, delta: Int) -> String
) {
    var encoderMode by remember { mutableStateOf(true) }

    val decoded = remember { mutableStateOf("") }
    val encoded = remember { mutableStateOf("") }
    var delta by remember { mutableStateOf(1) }

    val showRadioButtons = remember { mutableStateOf(false) }

    LaunchedEffect(decoded.value, encoded.value, delta) {
        if (encoderMode)
            encoded.value = encode(decoded.value, delta)
        else
            decoded.value = decode(encoded.value, delta)
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
                showRadioButtons = showRadioButtons
            )

            Column(
                modifier = Modifier
                    .padding(start = 56.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Delta = $delta",
                    style = MaterialTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("1")

                    Slider(
                        modifier = Modifier.weight(1f),
                        value = delta.toFloat(),
                        steps = 25,
                        onValueChange = { delta = it.roundToInt() },
                        valueRange = 1f..25f
                    )

                    Text("25")
                }
            }

            RadioButtonOptionalTextField(
                selected = !encoderMode,
                onClick = { encoderMode = false },
                hint = "Cipher text",
                input = encoded,
                showRadioButtons = showRadioButtons
            )
        }
    }
}