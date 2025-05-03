package com.faangx.ktp.test

import ksp.MiniAppTest

@MiniAppTest
object IntListOperationsP5Test {

    fun binarySearch(list: List<Int>, x: Int): Int {
        var start = 0; var end = list.lastIndex

        while (start <= end) {
            val midIndex = (start + end) / 2
            when {
                // x found
                list[midIndex] == x -> return midIndex

                // x might be in left half
                list[midIndex] > x -> end = midIndex - 1

                // x might be in right half
                else -> start = midIndex + 1
            }
        }

        // x not found
        return -1
    }

}