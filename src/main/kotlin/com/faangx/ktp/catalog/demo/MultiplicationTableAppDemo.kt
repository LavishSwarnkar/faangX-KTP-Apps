package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MultiplicationTableApp
import com.faangx.ktp.basics.MultiplicationTableAppV1
import com.faangx.ktp.util.print
import com.faangx.ktp.util.println

fun MultiplicationTableApp.printTable(num: Int, start: Int, end: Int) {
    for (i in start..end) {
        println("$num X $i = ${num * i}")
    }
}

fun MultiplicationTableApp.printTable(num: Int) {
    repeat(10) {
        println("$num X ${it + 1} = ${num * (it + 1)}")
    }
}

@Composable
fun MultiplicationTableAppDemo() {
    MultiplicationTableApp { num ->
        printTable(num)
    }
}

@Composable
fun MultiplicationTableAppV1Demo() {
    MultiplicationTableAppV1 { num, start, end ->
        printTable(num, start, end)
    }
}