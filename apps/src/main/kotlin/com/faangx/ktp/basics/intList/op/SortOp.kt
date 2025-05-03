package com.faangx.ktp.basics.intList.op

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.RadioGroup
import com.faangx.ktp.basics.intList.model.IntListOp

sealed interface SortOp: IntListOp {
    class Basic(
        val sort: (list: MutableList<Int>) -> Unit
    ): SortOp {
        override val label: String = "Sort"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }
    class WithDescendingSupport(
        val sort: (list: MutableList<Int>, descending: Boolean) -> Unit
    ): SortOp {
        override val label: String = "Sort"
        override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
    }
}

@Composable
fun SortOp.Comp(list: MutableList<Int>) {
    var result by remember { mutableStateOf("") }
    var descending by remember { mutableStateOf(false) }

    LaunchedEffect(list.joinToString(","), descending) {
        val listCopy = mutableListOf(*list.toTypedArray())
        when (this@Comp) {
            is SortOp.Basic -> sort(listCopy)
            is SortOp.WithDescendingSupport -> sort(listCopy, descending)
        }
        result = listCopy.joinToString(",")
    }

    if (this is SortOp.WithDescendingSupport) {
        RadioGroup(
            selection = if (descending) "Descending" else "Ascending",
            onSelectionChange = { descending = it == "Descending" },
            options = listOf("Descending", "Ascending")
        )
    }

    OutlinedTextField(
        modifier = Modifier.width(500.dp),
        label = { Text("Sorted Numbers") },
        value = result,
        onValueChange = { },
        readOnly = true,
        textStyle = MaterialTheme.typography.titleLarge.copy(
            textAlign = TextAlign.Center
        )
    )
}