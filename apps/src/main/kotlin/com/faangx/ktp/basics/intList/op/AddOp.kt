package com.faangx.ktp.basics.intList.op

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.Layout
import com.faangx.ktp.basics.RadioGroup
import com.faangx.ktp.basics.intList.model.IntListOp
import com.faangx.ktp.basics.intList.op.AddType.*

sealed interface AddOp: IntListOp {
    class InPlace(
        val addAt: (MutableList<Int>, Int, Int) -> Unit,
        val addAtStart: (MutableList<Int>, Int) -> Unit,
        val addAtEnd: (MutableList<Int>, Int) -> Unit
    ): AddOp {
        override val label: String = "Add Element (In Place)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }

    class NewList(
        val addAt: (List<Int>, Int, Int) -> List<Int>,
        val addAtStart: (List<Int>, Int) -> List<Int>,
        val addAtEnd: (List<Int>, Int) -> List<Int>
    ): AddOp {
        override val label: String = "Add Element (New List)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }
}

enum class AddType(val label: String) {
    Start("Start"),
    End("End"),
    Custom("Custom Index")
}

@Composable
fun AddOp.Comp(list: MutableList<Int>) {
    var index by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    var type by remember { mutableStateOf(Start) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioGroup(
            selection = type,
            options = AddType.entries.toList(),
            onSelectionChange = { type = it },
            labelExtractor = { it.name },
            layout = Layout.Column
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            AnimatedVisibility(type == Custom) {

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
            }

            OutlinedTextField(
                modifier = Modifier.width(130.dp)
                    .padding(horizontal = 12.dp),
                value = value,
                onValueChange = { value = it },
                label = { Text("Element") },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                )
            )

            Button(
                modifier = Modifier.padding(start = 12.dp),
                onClick = {
                    val valueInt = value.toIntOrNull() ?: return@Button

                    when (this@Comp) {
                        is AddOp.InPlace -> {
                            when (type) {
                                Start -> addAtStart(list, valueInt)
                                End -> addAtEnd(list, valueInt)
                                Custom -> {
                                    val indexInt = index.toIntOrNull() ?: return@Button
                                    addAt(list, indexInt, valueInt)
                                }
                            }
                        }
                        is AddOp.NewList -> {
                            val newList = when (type) {
                                Start -> addAtStart(list, valueInt)
                                End -> addAtEnd(list, valueInt)
                                Custom -> {
                                    val indexInt = index.toIntOrNull() ?: return@Button
                                    addAt(list, indexInt, valueInt)
                                }
                            }
                            list.clear()
                            list.addAll(newList)
                        }
                    }
                }
            ) {
                Text("ADD")
            }
        }
    }
}