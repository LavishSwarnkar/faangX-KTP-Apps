package com.faangx.ktp.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.streamliners.utils.safeLet

enum class NamingSystem(
    val powersOfTen: Map<Long, String>
) {
    Indian(
        mapOf(
            1_00_00_00_00_00_000L to "Nil",
            1_00_00_00_00_000L to "Kharab",
            1_00_00_00_000L to "Arab",
            1_00_00_000L to "Crore",
            1_00_000L to "Lakh",
            1_000L to "Thousand",
            100L to "Hundred",
        )
    ),

    International(
        mapOf(
            1_000_000_000_000L to "Trillion",
            1_000_000_000L to "Billion",
            1_000_000L to "Million",
            1_000L to "Thousand",
            100L to "Hundred",
        )
    )
}

fun NumberNameConvertorMiniApp(
    getName: (Long, NamingSystem) -> String,
    getFormattedNum: (Long, NamingSystem) -> String
) {
    MiniApp(
        title = "Number Name Convertor",
        composable = {
            NumberNameConvertor(getName, getFormattedNum)
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumberNameConvertor(
    getName: (Long, NamingSystem) -> String,
    getFormattedNum: (Long, NamingSystem) -> String
) {
    val system = remember { mutableStateOf<NamingSystem?>(NamingSystem.Indian) }

    val num = remember { mutableStateOf("") }
    val formattedNum = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }

    val showRadioButtons = remember { mutableStateOf(false) }

    LaunchedEffect(num.value, system.value) {
        safeLet(
            num.value.toLongOrNull(),
            system.value
        ) { num1, system1 ->
            name.value = getName(num1, system1)
            formattedNum.value = getFormattedNum(num1, system1)
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
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                label = { Text("Number") },
                value = num.value,
                onValueChange = { input ->
                    num.value = input.filter { it.isDigit() }
                        .take(15)
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                )
            )

            RadioGroup(
                state = system,
                options = NamingSystem.entries.toList(),
                labelExtractor = { it.name },
                layout = Layout.Row
            )

            Text(
                text = "=",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = formattedNum.value,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = name.value,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}