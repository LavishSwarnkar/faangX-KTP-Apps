package com.faangx.ktp.demo

import com.faangx.ktp.basics.MultiplicationTableMiniApp
import com.faangx.ktp.basics.MultiplicationTableV1MiniApp

fun printTable(num: Int, start: Int, end: Int) {
    for (i in start..end) {
        println("$num X $i = ${num * i}")
    }
}

fun printTable(num: Int) {
    repeat(10) {
        println("$num X ${it + 1} = ${num * (it + 1)}")
    }
}

fun main() {
//    MultiplicationTableMiniApp(::printTable)
    MultiplicationTableV1MiniApp(::printTable)
}