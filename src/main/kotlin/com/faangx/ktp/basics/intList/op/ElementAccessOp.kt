package com.faangx.ktp.basics.intList.op

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.basics.intList.model.IntListOp

class ElementAccessOp(
    val elementAt: (List<Int>, Int) -> Int?
): IntListOp {
    override val label: String = "Element Access"
    override val Composable: @Composable (List<Int>) -> Unit = { Comp(it) }
}

@Composable
fun ElementAccessOp.Comp(list: List<Int>) {
    var index by remember { mutableStateOf("") }

    var result by remember { mutableStateOf(SMILE_EMOJI) }

    LaunchedEffect(index) {
        val indexInt = index.toIntOrNull()
        result = if (indexInt != null) {
            val element = elementAt(list, indexInt)
            if (element == null) "doesn't exist" else "is $element"
        } else {
            SMILE_EMOJI
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Element at",
            style = MaterialTheme.typography.titleLarge
        )

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
            result,
            style = MaterialTheme.typography.titleLarge
        )
    }
}