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
import com.faangx.ktp.basics.LabelledCheckBox
import com.faangx.ktp.comp.RadioButtonOptionalTextField
import com.faangx.ktp.comp.ScreenSize
import com.faangx.ktp.comp.iz
import com.faangx.ktp.comp.rememberScreenSize
import ksp.MiniApp
import kotlin.math.roundToInt

@MiniApp(
    "Ceaser Cipher",
    "ProgrammingFundamentals/Ep4/CeaserCipher",
    "text, delta, negative; text, delta, negative"
)
@Composable
fun CeaserCipher(
    encode: (String, delta: Int, negative: Boolean) -> String,
    decode: (String, delta: Int, negative: Boolean) -> String
) {
    CeaserCipher(
        encode, decode, true
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

    val screenSize = rememberScreenSize()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 800.dp)
                .run {
                    if (screenSize.iz(ScreenSize.Large)) {
                        showRadioButtons.value = false
                        onPointerEvent(PointerEventType.Enter) { showRadioButtons.value = true }
                            .onPointerEvent(PointerEventType.Exit) { showRadioButtons.value = false }
                    } else {
                        showRadioButtons.value = true
                        this
                    }
                },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            RadioButtonOptionalTextField(
                modifier = Modifier.fillMaxWidth()
                    .widthIn(max = 800.dp),
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
                modifier = Modifier.fillMaxWidth()
                    .widthIn(max = 800.dp),
                selected = !encoderMode,
                onClick = { encoderMode = false },
                hint = "Cipher text",
                input = encoded,
                showRadioButtons = showRadioButtons
            )
        }
    }
}