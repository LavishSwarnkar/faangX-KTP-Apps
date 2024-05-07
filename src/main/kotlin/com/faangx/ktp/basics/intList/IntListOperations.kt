package com.faangx.ktp.basics.intList

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.intList.model.IntListOp
import com.faangx.ktp.basics.intList.model.IntListOpsVariant
import com.streamliners.compose.comp.spinner.OutlinedSpinner
import com.streamliners.compose.comp.spinner.state.SpinnerState
import com.streamliners.compose.comp.textInput.state.TextInputState

@Composable
fun IntListOperations(
    variant: IntListOpsVariant
) {
    val nums = remember { mutableStateOf("") }
    val list = remember { mutableStateListOf<Int>() }

    LaunchedEffect(nums.value) {
        list.clear()
        list.addAll(
            nums.value.split(",")
                .mapNotNull { it.trim().toIntOrNull() }
        )
    }

    LaunchedEffect(list.joinToString(",")) {
        nums.value = list.joinToString(",")
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier.width(500.dp),
                label = { Text("Numbers") },
                value = nums.value,
                onValueChange = { input ->
                    nums.value = input.filter { it.isDigit() || it in listOf(',', '-') }
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                )
            )

            when (variant) {
                is IntListOpsVariant.All -> {

                    val selection: MutableState<IntListOp?> = remember { mutableStateOf(variant.ops[3]) }
                    val labelState = remember {
                        mutableStateOf(
                            TextInputState("Operation")
                        )
                    }

                    OutlinedSpinner(
                        modifier = Modifier.width(300.dp),
                        options = variant.ops,
                        state = SpinnerState(
                            selection = selection,
                            textInputState = labelState,
                            labelExtractor = { it.label }
                        )
                    )

                    selection.value?.let { it.Composable(list) }
                }

                is IntListOpsVariant.Single -> {
                    Text(variant.op.label)

                    variant.op.Composable(list)
                }
            }
        }
    }
}