package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MultiplicationTableApp
import com.faangx.ktp.util.println
import java.io.ByteArrayOutputStream

fun ByteArrayOutputStream.printTable(num: Int, start: Int, end: Int) {
    for (i in start..end) {
        println("$num X $i = ${num * i}")
    }
}

@Composable
fun MultiplicationTableAppDemo() {
    MultiplicationTableApp { num, start, end ->
        printTable(num, start, end)
    }
}