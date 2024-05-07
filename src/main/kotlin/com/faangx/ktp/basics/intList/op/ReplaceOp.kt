package com.faangx.ktp.basics.intList.op

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.Layout
import com.faangx.ktp.basics.RadioGroup
import com.faangx.ktp.basics.intList.model.IntListOp

sealed interface ReplaceOp: IntListOp {

    class InPlace(
        val replace: (MutableList<Int>, Int, Int) -> Unit,
        val replaceAll: (MutableList<Int>, Int, Int) -> Unit
    ): ReplaceOp {
        override val label: String = "Replace Element (In Place)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }

    class NewList(
        val replace: (List<Int>, Int, Int) -> List<Int>,
        val replaceAll: (List<Int>, Int, Int) -> List<Int>
    ): ReplaceOp {
        override val label: String = "Replace Element (New List)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }
}

@Composable
fun ReplaceOp.Comp(list: MutableList<Int>) {
    var element by remember { mutableStateOf("") }
    var replacement by remember { mutableStateOf("") }
    var all by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        RadioGroup(
            selection = if (all) "All" else "First",
            options = listOf("First", "All"),
            onSelectionChange = { all = it == "All" },
            layout = Layout.Row
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.width(130.dp)
                    .padding(horizontal = 12.dp),
                value = element,
                onValueChange = { element = it },
                label = { Text("Element") },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                )
            )

            OutlinedTextField(
                modifier = Modifier.width(170.dp)
                    .padding(horizontal = 12.dp),
                value = replacement,
                onValueChange = { replacement = it },
                label = { Text("Replacement") },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                )
            )

            Button(
                modifier = Modifier.padding(start = 12.dp),
                onClick = {
                    val elementInt = element.toIntOrNull() ?: return@Button
                    val replacementInt = replacement.toIntOrNull() ?: return@Button

                    when (this@Comp) {
                        is ReplaceOp.InPlace -> {
                            if (all) {
                                replaceAll(list, elementInt, replacementInt)
                            } else {
                                replace(list, elementInt, replacementInt)
                            }
                        }
                        is ReplaceOp.NewList -> {
                            val newList = if (all) {
                                replaceAll(list, elementInt, replacementInt)
                            } else {
                                replace(list, elementInt, replacementInt)
                            }

                            list.clear()
                            list.addAll(newList)
                        }
                    }
                }
            ) {
                Text("REPLACE")
            }
        }
    }
}