package com.faangx.ktp.test

import ksp.MiniAppTest

@MiniAppTest
object IntListOperationsP6ATest {

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
}