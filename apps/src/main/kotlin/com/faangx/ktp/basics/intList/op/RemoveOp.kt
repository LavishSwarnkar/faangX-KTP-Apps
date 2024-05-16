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

sealed interface RemoveOp: IntListOp {
    class InPlace(
        val removeAt: (MutableList<Int>, Int) -> Unit,
        val remove: (MutableList<Int>, Int, Boolean) -> Unit,
    ): RemoveOp {
        override val label: String = "Remove Element (In Place)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }

    class NewList(
        val removeAt: (List<Int>, Int) -> List<Int>,
        val remove: (List<Int>, Int, Boolean) -> List<Int>,
    ): RemoveOp {
        override val label: String = "Remove Element (New List)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }
}

@Composable
fun RemoveOp.Comp(list: MutableList<Int>) {
    var what by remember { mutableStateOf("") }
    var byElement by remember { mutableStateOf(false) }
    var all by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioGroup(
            selection = if (byElement) "By Element" else "By Index",
            options = listOf("By Element", "By Index"),
            onSelectionChange = { byElement = it == "By Element" },
            layout = Layout.Row
        )

        AnimatedVisibility(byElement) {
            RadioGroup(
                selection = if (all) "All" else "First",
                options = listOf("First", "All"),
                onSelectionChange = { all = it == "All" },
                layout = Layout.Row
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.width(130.dp)
                    .padding(horizontal = 12.dp),
                value = what,
                onValueChange = { what = it },
                label = {
                    Text(
                        if (byElement) "Element" else "Index"
                    )
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                )
            )

            Button(
                modifier = Modifier.padding(start = 12.dp),
                onClick = {
                    val whatInt = what.toIntOrNull() ?: return@Button

                    when (this@Comp) {
                        is RemoveOp.InPlace -> {
                            if (byElement) {
                                remove(list, whatInt, all)
                            } else {
                                removeAt(list, whatInt)
                            }
                        }
                        is RemoveOp.NewList -> {
                            val newList = if (byElement) {
                                remove(list, whatInt, all)
                            } else {
                                removeAt(list, whatInt)
                            }

                            list.clear()
                            list.addAll(newList)
                        }
                    }
                }
            ) {
                Text("REMOVE")
            }
        }
    }
}