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
import com.faangx.ktp.MiniApp
import com.faangx.ktp.comp.RadioButtonOptionalTextField
import com.streamliners.compose.comp.select.LabelledCheckBox
import kotlin.math.roundToInt

fun CeaserCipherMiniApp(
    encode: (String, delta: Int) -> String,
    decode: (String, delta: Int) -> String
) {
    MiniApp(
        title = "CeaserCipher App",
        composable = {
            CeaserCipher(
                encode = { str, delta, _ ->
                    encode(str, delta)
                },
                decode = { str, delta, _ ->
                    decode(str, delta)
                },
                allowNegativeDelta = false
            )
        }
    )
}

fun CeaserCipherMiniAppV1(
    encode: (String, delta: Int, negative: Boolean) -> String,
    decode: (String, delta: Int, negative: Boolean) -> String
) {
    MiniApp(
        title = "CeaserCipher App",
        composable = {
            CeaserCipher(
                encode, decode, true
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CeaserCipher(
    encode: (String, delta: Int, negative: Boolean) -> String,
    decode: (String, delta: Int, negative: Boolean) -> String,
    allowNegativeDelta: Boolean
) {
    var encoderMode by remember { mutableStateOf(true) }

    val decoded = remember { mutableStateOf("") }
    val encoded = remember { mutableStateOf("") }
    var delta by remember { mutableStateOf(1) }
    val negative = remember { mutableStateOf(false) }

    val showRadioButtons = remember { mutableStateOf(false) }

    LaunchedEffect(decoded.value, encoded.value, delta, negative.value) {
        if (encoderMode)
            encoded.value = encode(decoded.value, delta, negative.value)
        else
            decoded.value = decode(encoded.value, delta, negative.value)
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Delta = ${if (negative.value) '-' else ' '}$delta",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (allowNegativeDelta) {
                        LabelledCheckBox(
                            modifier = Modifier.padding(start = 16.dp),
                            label = "Negative",
                            state = negative
                        )
                    }
                }

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