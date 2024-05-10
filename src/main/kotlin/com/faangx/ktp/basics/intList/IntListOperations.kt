package com.faangx.ktp.basics.intList

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.intList.model.IntListOp
import com.faangx.ktp.basics.intList.model.IntListOpsVariant
import com.streamliners.compose.comp.spinner.OutlinedSpinner
import com.streamliners.compose.comp.spinner.state.SpinnerState
import com.streamliners.compose.comp.textInput.state.TextInputState
import java.util.*

private fun randomList(size: Int): List<Int> {
    return buildList {
        val random = Random()
        repeat(size) { add(random.nextInt(100) + 1) }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
            var showGenerateButton by remember { mutableStateOf(false) }


            Row {
                OutlinedTextField(
                    modifier = Modifier.width(500.dp)
                        .onPointerEvent(PointerEventType.Enter) { showGenerateButton = true }
                        .onPointerEvent(PointerEventType.Exit) { showGenerateButton = false },
                    label = { Text("Numbers") },
                    value = nums.value,
                    onValueChange = { input ->
                        nums.value = input.filter { it.isDigit() || it in listOf(',', '-') }
                    },
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                    trailingIcon = {
                        AnimatedVisibility(
                            modifier = Modifier.padding(end = 12.dp),
                            visible = showGenerateButton,
                            enter = fadeIn() + expandHorizontally(
                                expandFrom = Alignment.Start
                            ),
                            exit = fadeOut() + shrinkHorizontally(
                                shrinkTowards = Alignment.Start
                            )
                        ) {
                            FilledIconButton(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                onClick = {
                                    nums.value = randomList(10).joinToString(",")
                                }
                            ) {
                                Icon(Icons.Default.Refresh, "Generate")
                            }
                        }
                    }
                )
            }

            when (variant) {
                is IntListOpsVariant.All -> {

                    val selection: MutableState<IntListOp?> = remember { mutableStateOf(variant.ops[11]) }
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