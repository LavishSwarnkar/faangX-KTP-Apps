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

class AddOp(
    val addAt: (MutableList<Int>, Int, Int) -> Unit,
    val addAtStart: (MutableList<Int>, Int) -> Unit,
    val addAtEnd: (MutableList<Int>, Int) -> Unit
): IntListOp {
    override val label: String = "Add Element"
    override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
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

                    when (type) {
                        Start -> addAtStart(list, valueInt)
                        End -> addAtEnd(list, valueInt)
                        Custom -> {
                            val indexInt = index.toIntOrNull() ?: return@Button
                            addAt(list, indexInt, valueInt)
                        }
                    }
                }
            ) {
                Text("ADD")
            }
        }
    }
}