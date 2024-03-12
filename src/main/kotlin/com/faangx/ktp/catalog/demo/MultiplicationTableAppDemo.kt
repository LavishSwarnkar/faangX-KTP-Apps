package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MultiplicationTableApp
import com.faangx.ktp.util.println

@Composable
fun MultiplicationTableAppDemo() {
    MultiplicationTableApp { num, start, end ->
        for (i in start..end) {
            println("$num X $i = ${num * i}")
        }
    }
}