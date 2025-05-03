package com.faangx.ktp.test

import ksp.MiniAppTest

@MiniAppTest
object IntListOperationsP6BTest {

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

}