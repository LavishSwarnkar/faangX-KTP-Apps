package com.faangx.ktp.basics.intList.op

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.intList.model.IntListOp

interface UpdateOp: IntListOp {
    class InPlace(
        val update: (MutableList<Int>, Int, Int) -> Unit
    ): UpdateOp {
        override val label: String = "Update Element (In Place)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }

    class NewList(
        val update: (List<Int>, Int, Int) -> List<Int>
    ): UpdateOp {
        override val label: String = "Update Element (New List)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }
}

@Composable
fun UpdateOp.Comp(list: MutableList<Int>) {

    var index by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.width(130.dp)
                .padding(horizontal = 12.dp),
            value = index,
            onValueChange = { index = it },
            label = { Text("Index") },
            textStyle = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            )
        )

        Text(
            " = ",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            modifier = Modifier.width(130.dp)
                .padding(horizontal = 12.dp),
            value = value,
            onValueChange = { value = it },
            label = { Text("Value") },
            textStyle = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            )
        )

        Button(
            modifier = Modifier.padding(start = 12.dp),
            onClick = {
                val indexInt = index.toIntOrNull() ?: return@Button
                val valueInt = value.toIntOrNull() ?: return@Button

                when (this@Comp) {
                    is UpdateOp.InPlace -> {
                        update(list, indexInt, valueInt)
                    }
                    is UpdateOp.NewList -> {
                        val newList = update(list, indexInt, valueInt)
                        list.clear()
                        list.addAll(newList)
                    }
                }

            }
        ) {
            Text("SET")
        }
    }
}