package com.faangx.ktp.basics.intList.op

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.basics.Layout
import com.faangx.ktp.basics.RadioGroup
import com.faangx.ktp.basics.intList.model.IntListOp

class FindOp(
    val find: (MutableList<Int>, Int) -> Int,
    val findAll: (MutableList<Int>, Int) -> List<Int>,
): IntListOp {
    override val label: String = "Find Element"
    override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
}

@Composable
fun FindOp.Comp(list: MutableList<Int>) {
    var element by remember { mutableStateOf("") }
    var all by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf(SMILE_EMOJI) }

    LaunchedEffect(list.joinToString(","), element, all) {
        val elementInt = element.toIntOrNull() ?: run {
            result = SMILE_EMOJI
            return@LaunchedEffect
        }

        result = if (all) {
            val indices = findAll(list, elementInt)
            if (indices.isEmpty()) {
                "Not found!"
            } else {
                "Found at indices : $indices"
            }
        } else {
            val foundAt = find(list, elementInt)
            if (foundAt == -1) {
                "Not found!"
            } else {
                "Found at index $foundAt"
            }
        }
    }

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

        Text(
            result,
            style = MaterialTheme.typography.titleLarge
        )
    }
}