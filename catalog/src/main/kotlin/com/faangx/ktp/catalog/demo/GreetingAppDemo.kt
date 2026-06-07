package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.GreetingApp

fun greet(name: String): String {
    return "Namaste $name"
}

@Composable
fun GreetingAppDemo() {
    GreetingApp(::greet)
}