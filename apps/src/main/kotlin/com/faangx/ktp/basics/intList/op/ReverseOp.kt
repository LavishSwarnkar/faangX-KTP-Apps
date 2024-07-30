package com.faangx.ktp.basics.intList.op

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.intList.model.IntListOp

sealed interface ReverseOp: IntListOp {
    class InPlace(
        val reverse: (MutableList<Int>) -> Unit,
    ): ReverseOp {
        override val label: String = "Reverse List (In Place)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }

    class NewList(
        val reverse: (List<Int>) -> List<Int>
    ): ReverseOp {
        override val label: String = "Reverse List (New List)"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }
}

@Composable
fun ReverseOp.Comp(list: MutableList<Int>) {
    var result by remember { mutableStateOf("") }

    LaunchedEffect(list.joinToString(",")) {
        val listCopy = mutableListOf(*list.toTypedArray())
        result = when (this@Comp) {
            is ReverseOp.InPlace -> reverse(listCopy).run { listCopy }
            is ReverseOp.NewList -> reverse(list)
        }.joinToString(",")
    }

    OutlinedTextField(
        modifier = Modifier.width(500.dp),
        label = { Text("Reversed Numbers") },
        value = result,
        onValueChange = { },
        readOnly = true,
        textStyle = MaterialTheme.typography.titleLarge.copy(
            textAlign = TextAlign.Center
        )
    )
}