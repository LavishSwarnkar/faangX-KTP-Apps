package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.faangx.ktp.basics.intList.op.*
import com.faangx.ktp.comp.GenerateButton
import com.streamliners.compose.comp.spinner.OutlinedSpinner
import com.streamliners.compose.comp.spinner.state.SpinnerState
import com.streamliners.compose.comp.textInput.state.TextInputState
import ksp.MiniApp
import java.util.*

private fun randomList(size: Int): List<Int> {
    return buildList {
        val random = Random()
        repeat(size) { add(random.nextInt(100) + 1) }
    }
}

private fun sort(list: MutableList<Int>) {
    for (i in list.lastIndex downTo 1) {

        // Flag to track whether at least 1 swap is performed
        var swapped = false

        for (j in 0..<i) {
            if (list[j] > list[j+1]) {
                list[j] = list[j+1].also {
                    list[j+1] = list[j]
                }
                swapped = true
            }
        }

        // If no swap performed, then list is sorted
        if (!swapped) break
    }
}

// TODO : Mathematical not updating when random generated list

@MiniApp(
    name = "IntListOps P1",
    repoPath = "ProgrammingFundamentals/Ep5/IntListOpsP1",
    paramNames = "list, index; list; list; list; list; list, element; list, element"
)
@Composable
fun IntListOperationsP1(
    elementAt: (List<Int>, Int) -> Int?,

    findMin: (List<Int>) -> Int?,
    findMax: (List<Int>) -> Int?,
    calculateSum: (List<Int>) -> Int?,
    calculateMean: (List<Int>) -> Float?,

    find: (MutableList<Int>, Int) -> Int,
    findAll: (MutableList<Int>, Int) -> List<Int>,
) {
    IntListOperations(
        IntListOpsVariant.All(
            listOf(
                ElementAccessOp(elementAt),
                MathematicalOp(findMin, findMax, calculateSum, calculateMean),
                FindOp(find, findAll)
            )
        )
    )
}

@MiniApp(
    name = "IntListOps P2",
    repoPath = "ProgrammingFundamentals/Ep5/IntListOpsP2",
    paramNames = "list, index, value; list, index, value; list, index, element; list, element; list, element; list, index, element; list, element; list, element"
)
@Composable
fun IntListOperationsP2(
    updateInPlace: (MutableList<Int>, Int, Int) -> Unit,
    updateNewList: (List<Int>, Int, Int) -> List<Int>,

    addAtInPlace: (MutableList<Int>, Int, Int) -> Unit,
    addAtStartInPlace: (MutableList<Int>, Int) -> Unit,
    addAtEndInPlace: (MutableList<Int>, Int) -> Unit,

    addAtNewList: (List<Int>, Int, Int) -> List<Int>,
    addAtStartNewList: (List<Int>, Int) -> List<Int>,
    addAtEndNewList: (List<Int>, Int) -> List<Int>
) {
    IntListOperations(
        IntListOpsVariant.All(
            listOf(
                UpdateOp.InPlace(updateInPlace),
                UpdateOp.NewList(updateNewList),
                AddOp.InPlace(addAtInPlace, addAtStartInPlace, addAtEndInPlace),
                AddOp.NewList(addAtNewList, addAtStartNewList, addAtEndNewList)
            )
        )
    )
}

@MiniApp(
    name = "IntListOps P3",
    repoPath = "ProgrammingFundamentals/Ep5/IntListOpsP3",
    paramNames = "list, index; list, index, all; list, index; list, index, all; list, element, replacement; list, element, replacement; list, element, replacement; list, element, replacement"
)
@Composable
fun IntListOperationsP3(
    removeAtInPlace: (MutableList<Int>, Int) -> Unit,
    removeInPlace: (MutableList<Int>, Int, Boolean) -> Unit,
    removeAtNewList: (List<Int>, Int) -> List<Int>,
    removeNewList: (List<Int>, Int, Boolean) -> List<Int>,
    replaceInPlace: (MutableList<Int>, Int, Int) -> Unit,
    replaceAllInPlace: (MutableList<Int>, Int, Int) -> Unit,
    replaceNewList: (List<Int>, Int, Int) -> List<Int>,
    replaceAllNewList: (List<Int>, Int, Int) -> List<Int>
) {
    IntListOperations(
        IntListOpsVariant.All(
            listOf(
                RemoveOp.InPlace(removeAtInPlace, removeInPlace),
                RemoveOp.NewList(removeAtNewList, removeNewList),
                ReplaceOp.InPlace(replaceInPlace, replaceAllInPlace),
                ReplaceOp.NewList(replaceNewList, replaceAllNewList),
            )
        )
    )
}

@MiniApp(
    name = "IntListOps P4",
    repoPath = "ProgrammingFundamentals/Ep5/IntListOpsP4",
    paramNames = "list; list"
)
@Composable
fun IntListOperationsP4(
    reverseInPlace: (MutableList<Int>) -> Unit,
    reverseNewList: (List<Int>) -> List<Int>
) {
    IntListOperations(
        IntListOpsVariant.All(
            listOf(
                ReverseOp.InPlace(reverseInPlace),
                ReverseOp.NewList(reverseNewList)
            )
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IntListOperations(
    variant: IntListOpsVariant
) {
    val nums = remember { mutableStateOf("") }
    val list = remember { mutableStateListOf<Int>() }

    var randomListSortIsRequired by remember { mutableStateOf(false) }

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
                        GenerateButton(
                            visible = showGenerateButton,
                            onClick = {
                                nums.value = randomList(10)
                                    .toMutableList()
                                    .apply {
                                        if (randomListSortIsRequired) sort(this)
                                    }
                                    .joinToString(",")
                            }
                        )
                    }
                )
            }

            when (variant) {
                is IntListOpsVariant.All -> {

                    val selection: MutableState<IntListOp?> = remember { mutableStateOf(variant.ops[0]) }
                    val labelState = remember {
                        mutableStateOf(
                            TextInputState("Operation")
                        )
                    }

                    LaunchedEffect(selection.value) {
                        randomListSortIsRequired = selection.value is BinarySearchOp
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
                    LaunchedEffect(variant) {
                        randomListSortIsRequired = variant.op is BinarySearchOp
                    }

                    Text(variant.op.label)

                    variant.op.Composable(list)
                }
            }
        }
    }
}