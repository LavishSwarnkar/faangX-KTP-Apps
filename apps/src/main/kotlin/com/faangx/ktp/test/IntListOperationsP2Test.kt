package com.faangx.ktp.test

import ksp.MiniAppTest

@MiniAppTest
object IntListOperationsP2Test {

    fun updateInPlace(list: MutableList<Int>, index: Int, value: Int) {
        list[index] = value
    }

    fun updateNewList(list: List<Int>, index: Int, value: Int): List<Int> {
        return buildList {
            for (i in list.indices) {
                if (i == index) add(value) else add(list[i])
            }
        }
    }

    fun addAtInPlace(list: MutableList<Int>, index: Int, element: Int) {
        list.add(index, element)
    }

    fun addAtStartInPlace(list: MutableList<Int>, element: Int) {
        list.add(0, element)
    }

    fun addAtEndInPlace(list: MutableList<Int>, element: Int) {
        list.add(element)
    }

    fun addAtNewList(list: List<Int>, index: Int, element: Int): List<Int> {
        return buildList {
            for (i in list.indices) {
                if (i == index) add(element)
                add(list[i])
            }
        }
    }

    fun addAtStartNewList(list: List<Int>, element: Int): List<Int> {
        return buildList {
            add(element)
            list.forEach { add(it) }
        }
    }

    fun addAtEndNewList(list: List<Int>, element: Int): List<Int> {
        return list + element
    }

}