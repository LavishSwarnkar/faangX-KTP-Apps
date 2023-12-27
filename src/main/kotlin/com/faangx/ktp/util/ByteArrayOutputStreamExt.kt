package com.faangx.ktp.util

import java.io.ByteArrayOutputStream

fun ByteArrayOutputStream.print(char: Char) =  print(char.toString())

fun ByteArrayOutputStream.print(int: Int) =  print(int.toString(16))

fun ByteArrayOutputStream.print(string: String) {
    write(string.toByteArray())
}

fun ByteArrayOutputStream.println(string: String = "") {
    write((string + "\n").toByteArray())
}