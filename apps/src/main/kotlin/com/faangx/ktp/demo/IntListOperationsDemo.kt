package com.faangx.ktp.demo

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.runtime.Composable
import com.faangx.ktp.MiniApp
import com.faangx.ktp.basics.IntListOperations
import com.faangx.ktp.basics.intList.model.IntListOpsVariant
import com.faangx.ktp.basics.intList.op.*

fun findMin(list: List<Int>): Int? {
    if (list.isEmpty()) return null
    var min = Int.MAX_VALUE
    for (element in list) {
        if (element < min) min = element
    }
    return min
}

fun findMax(list: List<Int>): Int {
    var max = Int.MIN_VALUE
    list.forEach {
        if (it > max) max = it
    }
    return max
}

fun calculateMean(list: List<Int>): Float {
    return calculateSum(list).toFloat() / list.size
}

fun calculateSum(list: List<Int>): Int {
    var sum = 0
    list.forEach { element -> sum += element }
    return sum
}

fun elementAt(list: List<Int>, index: Int): Int? {
    if (index < 0 || index > list.lastIndex) return null
    return list[index]
}

fun update(list: MutableList<Int>, index: Int, value: Int) {
    list[index] = value
}

fun update(list: List<Int>, index: Int, value: Int): List<Int> {
    return buildList {
        for (i in list.indices) {
            if (i == index) add(value) else add(list[i])
        }
    }
}

fun remove(
    list: MutableList<Int>,
    element: Int,
    all: Boolean
) {
    if (all) {
        while (list.contains(element)) {
            list.remove(element)
        }
        // list.removeAll { it == element }
    } else {
        list.remove(element)
    }
}

fun removeAt(
    list: MutableList<Int>,
    index: Int
) {
    list.removeAt(index)
}

fun remove(
    list: List<Int>,
    element: Int,
    all: Boolean
): List<Int> {
    return buildList {
        var foundOne = false

        for (x in list) {
            if (x == element) {
                if (all) continue

                if (!foundOne) {
                    foundOne = true
                    continue
                }
            }

            add(x)
        }
    }
}

fun removeAt(
    list: List<Int>,
    index: Int
): List<Int> {
    return buildList {
        for (i in list.indices) {
            if (i != index) add(list[i])
        }
    }
}

fun addAt(list: MutableList<Int>, index: Int, element: Int) {
    list.add(index, element)
}

fun addAtStart(list: MutableList<Int>, element: Int) {
    list.add(0, element)
}

fun addAtEnd(list: MutableList<Int>, element: Int) {
    list.add(element)
}

fun addAt(list: List<Int>, index: Int, element: Int): List<Int> {
    return buildList {
        for (i in list.indices) {
            if (i == index) add(element)
            add(list[i])
        }
    }
}

fun addAtStart(list: List<Int>, element: Int): List<Int> {
    return buildList {
        add(element)
        list.forEach { add(it) }
    }
}

fun addAtEnd(list: List<Int>, element: Int): List<Int> {
    return list + element
}

fun find(list: List<Int>, element: Int): Int {
    for (i in list.indices) {
        if (list[i] == element) return i
    }
    return -1
}

fun findAll(list: List<Int>, element: Int): List<Int> {
    return buildList {
        for (i in list.indices) {
            if (list[i] == element) add(i)
        }
    }
}

fun binarySearch(list: List<Int>, x: Int): Int {
    var begin = 0; var end = list.lastIndex

    while (begin <= end) {
        val midIndex = (begin + end) / 2
        when {
            // x found
            list[midIndex] == x -> return midIndex

            // x might be in first half
            list[midIndex] > x -> end = midIndex - 1

            // x might be in second half
            else -> begin = midIndex + 1
        }
    }

    // x not found
    return -1
}

fun replace(
    list: MutableList<Int>,
    element: Int,
    replacement: Int
) {
    val index = list.indexOf(element)
    if (index != -1) {
        list[index] = replacement
    }
}

fun replaceAll(
    list: MutableList<Int>,
    element: Int,
    replacement: Int
) {
    for (i in list.indices) {
        if (list[i] == element) list[i] = replacement
    }
}

fun replace(
    list: List<Int>,
    element: Int,
    replacement: Int
): List<Int> {
    val index = list.indexOf(element)
    return if (index != -1) {
        buildList {
            for (i in list.indices) {
                if (i == index) {
                    add(replacement)
                } else {
                    add(list[i])
                }
            }
        }
    } else list
}

fun replaceAll(
    list: List<Int>,
    element: Int,
    replacement: Int
): List<Int> {
    return buildList {
        for (i in list) {
            if (i == element) {
                add(replacement)
            } else {
                add(i)
            }
        }
    }
}

fun sort(list: MutableList<Int>) {
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

fun sort(list: MutableList<Int>, descending: Boolean) {
    for (i in list.lastIndex downTo 1) {

        // Flag to track whether at least 1 swap is performed
        var swapped = false

        for (j in 0..<i) {

            // Determine Swap condition
            val swap = if (descending) {
                list[j] < list[j+1]
            } else {
                list[j] > list[j+1]
            }

            if (swap) {
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

fun main() {
    MiniApp(
        title = "IntListOps"
    ) {
        IntListOperationsDemo()
    }
}

@Composable
fun IntListOperationsDemo() {
    IntListOperations(
        IntListOpsVariant.All(
            listOf(
                ElementAccessOp(::elementAt),
                MathematicalOp(::findMin, ::findMax, ::calculateSum, ::calculateMean),
                FindOp(::find, ::findAll),
                AddOp.InPlace(::addAt, ::addAtStart, ::addAtEnd),
                AddOp.NewList(::addAt, ::addAtStart, ::addAtEnd),
                UpdateOp.InPlace(::update),
                UpdateOp.NewList(::update),

                RemoveOp.InPlace(::removeAt, ::remove),
                RemoveOp.NewList(::removeAt, ::remove),
                BinarySearchOp(::binarySearch),
                ReplaceOp.InPlace(::replace, ::replaceAll),
                ReplaceOp.NewList(::replace, ::replaceAll),
                SortOp(::sort, ::sort, false)
            )
        )
    )
}