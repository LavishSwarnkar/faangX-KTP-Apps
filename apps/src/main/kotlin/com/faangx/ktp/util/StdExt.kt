package com.faangx.ktp.util

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import kotlin.io.path.Path
import kotlin.io.path.absolute

fun captureStdOutput(lambda: () -> Unit): String {
    val stream = ByteArrayOutputStream()
    val new = PrintStream(stream, true, StandardCharsets.UTF_8.name())
    val prev = System.out

    System.setOut(new)
    lambda()
    System.setOut(prev)

    return stream.toString()
}

fun getResFile(fileName: String = "temp.txt"): File {
    val file = Path("apps/src/main/resources", fileName).absolute().toFile()
    if (!file.parentFile.exists())
        file.parentFile.mkdirs()
    if (!file.exists())
        file.createNewFile()
    return file
}