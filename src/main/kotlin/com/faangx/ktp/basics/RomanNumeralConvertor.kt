package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.basics.Conversion.*
import com.faangx.ktp.comp.RadioButtonOptionalTextField

private enum class Conversion { ToNumeral, ToNum }

fun RomanNumeralConvertorMiniApp(
    getNumeral: (Int) -> String,
    getNum: (String) -> Int
) {
    MiniApp(
        title = "Roman Numeral Convertor",
        composable = {
            RomanNumeralConvertor(
                getNumeral, getNum
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RomanNumeralConvertor(
    getNumeral: (Int) -> String,
    getNum: (String) -> Int
) {
    var type by remember { mutableStateOf(ToNumeral) }

    val num = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }

    val showRadioButtons = remember { mutableStateOf(false) }

    LaunchedEffect(num.value, name.value) {
        when (type) {
            ToNumeral -> {
                name.value = num.value.toIntOrNull()?.let(getNumeral) ?: ""
            }
            ToNum -> {
                num.value = name.value.ifBlank { null }?.let(getNum)?.toString() ?: ""
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .onPointerEvent(PointerEventType.Enter) { showRadioButtons.value = true }
                .onPointerEvent(PointerEventType.Exit) { showRadioButtons.value = false },
        ) {

            RadioButtonOptionalTextField(
                selected = type == ToNumeral,
                onClick = { type = ToNumeral },
                hint = "Number",
                value = num.value,
                onValueChange = { input ->
                    num.value = input.filter { it.isDigit() }
                },
                showRadioButtons = showRadioButtons
            )

            RadioButtonOptionalTextField(
                selected = type == ToNum,
                onClick = { type = ToNum },
                hint = "Roman Numeral",
                value = name.value,
                onValueChange = { input ->
                    name.value = input.filter { it.isLetter() }
                },
                showRadioButtons = showRadioButtons
            )
        }
    }
}