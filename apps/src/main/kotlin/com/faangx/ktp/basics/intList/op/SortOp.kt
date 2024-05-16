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

class SortOp(
    val sort: (list: MutableList<Int>) -> Unit,
    val sort1: (list: MutableList<Int>, descending: Boolean) -> Unit,
    val allowDescending: Boolean
): IntListOp {
    override val label: String = "Sort"
    override val Composable: @Composable (MutableList<Int>) -> Unit = { Comp(it) }
}

@Composable
fun SortOp.Comp(list: MutableList<Int>) {
    var result by remember { mutableStateOf("") }
    var descending by remember { mutableStateOf(false) }

    LaunchedEffect(list.joinToString(","), descending) {
        val listCopy = mutableListOf(*list.toTypedArray())
        if (descending) sort1(listCopy, true) else sort(listCopy)
        result = listCopy.joinToString(",")
    }

    if (allowDescending) {
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