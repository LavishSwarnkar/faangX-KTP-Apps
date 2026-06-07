package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.TextApp

fun getText(): String {
    return "Vande Mataram"
}

@Composable
fun TextAppDemo() {
    TextApp(::getText)
}