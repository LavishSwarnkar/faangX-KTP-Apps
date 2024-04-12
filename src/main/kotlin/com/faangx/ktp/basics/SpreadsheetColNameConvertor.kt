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
import com.faangx.ktp.basics.Type.*
import com.faangx.ktp.comp.RadioButtonOptionalTextField

enum class Type { ToName, ToNum }

fun SpreadsheetColNameConvertorMiniApp(
    getSpreadsheetColName: (Int) -> String,
    getColNum: (String) -> Int
) {
    MiniApp(
        title = "Spreadsheet Column Name Convertor",
        composable = {
            SpreadsheetColNameConvertor(
                getSpreadsheetColName, getColNum
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpreadsheetColNameConvertor(
    getSpreadsheetColName: (Int) -> String,
    getColNum: (String) -> Int
) {
    var type by remember { mutableStateOf(ToName) }

    val num = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }

    val showRadioButtons = remember { mutableStateOf(false) }

    LaunchedEffect(num.value, name.value) {
        when (type) {
            ToName -> {
                name.value = num.value.toIntOrNull()?.let(getSpreadsheetColName) ?: ""
            }
            ToNum -> {
                num.value = name.value.ifBlank { null }?.let(getColNum)?.toString() ?: ""
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
                selected = type == ToName,
                onClick = { type = ToName },
                hint = "Column Number",
                value = num.value,
                onValueChange = { input ->
                    num.value = input.filter { it.isDigit() }
                },
                showRadioButtons = showRadioButtons
            )

            RadioButtonOptionalTextField(
                selected = type == ToNum,
                onClick = { type = ToNum },
                hint = "Column Name",
                value = name.value,
                onValueChange = { input ->
                    name.value = input.filter { it.isLetter() }
                },
                showRadioButtons = showRadioButtons
            )
        }
    }
}