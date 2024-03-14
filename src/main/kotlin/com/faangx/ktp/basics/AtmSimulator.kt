package com.faangx.ktp.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.Denomination.Type.Coin
import com.faangx.ktp.basics.Denomination.Type.Note
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.nullableValue

class Denomination(
    val value: Int,
    val type: Type
) {
    enum class Type { Note, Coin }

    companion object {
        val INRDenominations = listOf(
            Denomination(2000, Note),
            Denomination(500, Note),
            Denomination(200, Note),
            Denomination(100, Note),
            Denomination(50, Note),
            Denomination(20, Note),
            Denomination(10, Note),
            Denomination(5, Coin),
            Denomination(2, Coin),
            Denomination(1, Coin)
        )
    }
}

typealias AtmOutput = Map<Denomination, Int>

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AtmSimulator(
    getAtmOutput: (Int) -> AtmOutput
) {
    val amount = remember { mutableStateOf(TextInputState("Amount")) }
    val denominations: MutableState<AtmOutput> = remember { mutableStateOf(emptyMap()) }

    LaunchedEffect(amount.value) {
        denominations.value = emptyMap()
        val input = amount.nullableValue()?.toIntOrNull() ?: return@LaunchedEffect
        denominations.value = getAtmOutput(input)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        TextInputLayout(
            modifier = Modifier.width(400.dp),
            state = amount
        )

        FlowRow {
            denominations.value.entries.forEach { (denom, qty) ->
                DenomCard(denom, qty)
            }
        }
    }
}

@Composable
fun DenomCard(denom: Denomination, qty: Int) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = denom.value.toString(),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "* $qty",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
