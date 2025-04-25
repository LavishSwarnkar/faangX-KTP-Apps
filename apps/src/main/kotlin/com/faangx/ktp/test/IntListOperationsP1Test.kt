package com.faangx.ktp.test

import ksp.MiniAppTest

@MiniAppTest
object IntListOperationsP1Test {

    fun elementAt(list: List<Int>, index: Int): Int? {
        if (index < 0 || index > list.lastIndex) return null
        return list[index]
    }

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

    fun calculateSum(list: List<Int>): Int {
        var sum = 0
        list.forEach { element -> sum += element }
        return sum
    }

    fun calculateMean(list: List<Int>): Float {
        return calculateSum(list).toFloat() / list.size
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

}