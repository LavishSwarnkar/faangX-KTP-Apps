@file:OptIn(ExperimentalComposeUiApi::class)

package com.faangx.ktp.basics

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.NumberSystem.*
import com.faangx.ktp.comp.RadioButtonOptionalTextField

enum class NumberSystem(val base: Int) {
    Decimal(10),
    Binary(2),
    Hexadecimal(16),
    Octal(8)
}

typealias ConvertNumber = (
    num: String,
    from: NumberSystem,
    to: NumberSystem
) -> String

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumberSystemConverter(convertNumber: ConvertNumber) {

    val decimal = remember { mutableStateOf("") }
    val binary = remember { mutableStateOf("") }
    val hexadecimal = remember { mutableStateOf("") }
    val octal = remember { mutableStateOf("") }

    val selection = remember { mutableStateOf<NumberSystem?>(null) }

    val showRadioButtons = remember { mutableStateOf(false) }

    fun inputStateFor(numberSystem: NumberSystem) = when (numberSystem) {
        Decimal -> decimal
        Binary -> binary
        Hexadecimal -> hexadecimal
        Octal -> octal
    }

    fun numberSystemOf(inputState: MutableState<String>) = when (inputState) {
        decimal -> Decimal
        binary -> Binary
        hexadecimal -> Hexadecimal
        octal -> Octal
        else -> error("No mapping found!")
    }

    LaunchedEffect(decimal.value, binary.value, hexadecimal.value, octal.value) {
        val selected = selection.value ?: return@LaunchedEffect

        val others = NumberSystem.entries.filter { it != selected }.toList()
        others.forEach { inputStateFor(it).value = "" }

        val input = inputStateFor(selected).value.ifBlank { null } ?: return@LaunchedEffect

        others.forEach { other ->
            inputStateFor(other).value = convertNumber(input, selected, other)
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            listOf(decimal, binary, hexadecimal, octal).forEach { state ->

                RadioButtonOptionalTextField(
                    selected = selection.value?.name == numberSystemOf(state).name,
                    onClick = { selection.value = numberSystemOf(state) },
                    hint = numberSystemOf(state).name,
                    input = state,
                    showRadioButtons = showRadioButtons
                )
            }
        }
    }
}