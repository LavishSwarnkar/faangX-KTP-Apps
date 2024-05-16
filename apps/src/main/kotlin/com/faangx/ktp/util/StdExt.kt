package com.faangx.ktp.util

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

fun captureStdOutput(lambda: () -> Unit): String {
    val stream = ByteArrayOutputStream()
    val new = PrintStream(stream, true, StandardCharsets.UTF_8.name())
    val prev = System.out

    System.setOut(new)
    lambda()
    System.setOut(prev)

    return stream.toString()
}