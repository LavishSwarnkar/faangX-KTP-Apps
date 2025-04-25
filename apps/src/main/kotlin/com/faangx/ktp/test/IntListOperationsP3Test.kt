package com.faangx.ktp.test

import ksp.MiniAppTest

@MiniAppTest
object IntListOperationsP3Test {

    fun removeInPlace(
        list: MutableList<Int>,
        element: Int,
        all: Boolean
    ) {
        if (all) {
            while (list.contains(element)) {
                list.remove(element)
            }
        } else {
            list.remove(element)
        }
    }

    fun removeAtInPlace(
        list: MutableList<Int>,
        index: Int
    ) {
        list.removeAt(index)
    }

    fun removeNewList(
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

    fun removeAtNewList(
        list: List<Int>,
        index: Int
    ): List<Int> {
        return buildList {
            for (i in list.indices) {
                if (i != index) add(list[i])
            }
        }
    }

    fun replaceInPlace(
        list: MutableList<Int>,
        element: Int,
        replacement: Int
    ) {
        val index = list.indexOf(element)
        if (index != -1) {
            list[index] = replacement
        }
    }

    fun replaceAllInPlace(
        list: MutableList<Int>,
        element: Int,
        replacement: Int
    ) {
        for (i in list.indices) {
            if (list[i] == element) list[i] = replacement
        }
    }

    fun replaceNewList(
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

    fun replaceAllNewList(
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

}