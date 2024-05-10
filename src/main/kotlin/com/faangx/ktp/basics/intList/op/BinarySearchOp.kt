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

class BinarySearchOp(
    val binarySearch: (MutableList<Int>, Int) -> Int
): IntListOp {
    override val label: String = "Binary Search"
    override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
}

@Composable
fun BinarySearchOp.Comp(list: MutableList<Int>) {
    var element by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(SMILE_EMOJI) }

    LaunchedEffect(list.joinToString(","), element) {
        val elementInt = element.toIntOrNull() ?: run {
            result = SMILE_EMOJI
            return@LaunchedEffect
        }

        val foundAt = binarySearch(list, elementInt)
        result = if (foundAt == -1) {
            "Not found!"
        } else {
            "Found at index $foundAt"
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
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

        Text(
            result,
            style = MaterialTheme.typography.titleLarge
        )
    }
}