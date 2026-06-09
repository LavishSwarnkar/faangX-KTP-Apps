package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.IntListOperations
import com.faangx.ktp.basics.intList.model.IntListOpsVariant
import com.faangx.ktp.basics.intList.op.AddOp
import com.faangx.ktp.basics.intList.op.BinarySearchOp
import com.faangx.ktp.basics.intList.op.ElementAccessOp
import com.faangx.ktp.basics.intList.op.FindOp
import com.faangx.ktp.basics.intList.op.MathematicalOp
import com.faangx.ktp.basics.intList.op.RemoveOp
import com.faangx.ktp.basics.intList.op.ReplaceOp
import com.faangx.ktp.basics.intList.op.ReverseOp
import com.faangx.ktp.basics.intList.op.SortOp
import com.faangx.ktp.basics.intList.op.UpdateOp

// Element access
fun elementAt(list: List<Int>, index: Int): Int? = list.getOrNull(index)

// Mathematical
fun findMin(list: List<Int>): Int? = list.minOrNull()
fun findMax(list: List<Int>): Int? = list.maxOrNull()
fun calculateSum(list: List<Int>): Int? = if (list.isEmpty()) null else list.sum()
fun calculateMean(list: List<Int>): Float? =
    if (list.isEmpty()) null else list.sum().toFloat() / list.size

// Find
fun find(list: MutableList<Int>, element: Int): Int = list.indexOf(element)
fun findAll(list: MutableList<Int>, element: Int): List<Int> =
    list.withIndex().filter { it.value == element }.map { it.index }

// Update
fun updateInPlace(list: MutableList<Int>, index: Int, value: Int) {
    list[index] = value
}
fun updateNewList(list: List<Int>, index: Int, value: Int): List<Int> =
    list.toMutableList().apply { this[index] = value }

// Add
fun addAtInPlace(list: MutableList<Int>, index: Int, element: Int) {
    list.add(index, element)
}
fun addAtStartInPlace(list: MutableList<Int>, element: Int) {
    list.add(0, element)
}
fun addAtEndInPlace(list: MutableList<Int>, element: Int) {
    list.add(element)
}
fun addAtNewList(list: List<Int>, index: Int, element: Int): List<Int> =
    list.toMutableList().apply { add(index, element) }
fun addAtStartNewList(list: List<Int>, element: Int): List<Int> = listOf(element) + list
fun addAtEndNewList(list: List<Int>, element: Int): List<Int> = list + element

// Remove
fun removeAtInPlace(list: MutableList<Int>, index: Int) {
    list.removeAt(index)
}
fun removeInPlace(list: MutableList<Int>, element: Int, all: Boolean) {
    if (all) {
        list.removeAll { it == element }
    } else {
        val index = list.indexOf(element)
        if (index != -1) list.removeAt(index)
    }
}
fun removeAtNewList(list: List<Int>, index: Int): List<Int> =
    list.toMutableList().apply { removeAt(index) }
fun removeNewList(list: List<Int>, element: Int, all: Boolean): List<Int> =
    if (all) {
        list.filter { it != element }
    } else {
        val index = list.indexOf(element)
        if (index == -1) list else list.toMutableList().apply { removeAt(index) }
    }

// Replace
fun replaceInPlace(list: MutableList<Int>, element: Int, replacement: Int) {
    val index = list.indexOf(element)
    if (index != -1) list[index] = replacement
}
fun replaceAllInPlace(list: MutableList<Int>, element: Int, replacement: Int) {
    for (i in list.indices) {
        if (list[i] == element) list[i] = replacement
    }
}
fun replaceNewList(list: List<Int>, element: Int, replacement: Int): List<Int> {
    val index = list.indexOf(element)
    return if (index == -1) list else list.toMutableList().apply { this[index] = replacement }
}
fun replaceAllNewList(list: List<Int>, element: Int, replacement: Int): List<Int> =
    list.map { if (it == element) replacement else it }

// Reverse
fun reverseInPlace(list: MutableList<Int>) {
    list.reverse()
}
fun reverseNewList(list: List<Int>): List<Int> = list.reversed()

// Binary search (assumes the list is sorted)
fun binarySearch(list: MutableList<Int>, x: Int): Int {
    var low = 0
    var high = list.size - 1
    while (low <= high) {
        val mid = (low + high) / 2
        when {
            list[mid] == x -> return mid
            list[mid] < x -> low = mid + 1
            else -> high = mid - 1
        }
    }
    return -1
}

// Sort
fun sort(list: MutableList<Int>, descending: Boolean) {
    if (descending) list.sortDescending() else list.sort()
}

@Composable
fun IntListOperationsDemo() {
    IntListOperations(
        IntListOpsVariant.All(
            listOf(
                ElementAccessOp(::elementAt),
                MathematicalOp(::findMin, ::findMax, ::calculateSum, ::calculateMean),
                FindOp(::find, ::findAll),
                UpdateOp.InPlace(::updateInPlace),
                UpdateOp.NewList(::updateNewList),
                AddOp.InPlace(::addAtInPlace, ::addAtStartInPlace, ::addAtEndInPlace),
                AddOp.NewList(::addAtNewList, ::addAtStartNewList, ::addAtEndNewList),
                RemoveOp.InPlace(::removeAtInPlace, ::removeInPlace),
                RemoveOp.NewList(::removeAtNewList, ::removeNewList),
                ReplaceOp.InPlace(::replaceInPlace, ::replaceAllInPlace),
                ReplaceOp.NewList(::replaceNewList, ::replaceAllNewList),
                ReverseOp.InPlace(::reverseInPlace),
                ReverseOp.NewList(::reverseNewList),
                BinarySearchOp(::binarySearch),
                SortOp.WithDescendingSupport(::sort),
            )
        )
    )
}
