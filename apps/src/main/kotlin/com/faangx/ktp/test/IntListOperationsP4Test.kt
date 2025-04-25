package com.faangx.ktp.test

import ksp.MiniAppTest

@MiniAppTest
object IntListOperationsP4Test {

    fun reverseInPlace(list: MutableList<Int>) {
        for (i in 0..<list.size / 2) {
            val temp = list[i]
            list[i] = list[list.lastIndex - i]
            list[list.lastIndex - i] = temp
        }
    }

    fun reverseNewList(list: List<Int>): List<Int> {
        return buildList {
            for (i in list.lastIndex downTo 0) {
                add(list[i])
            }
        }
    }

}