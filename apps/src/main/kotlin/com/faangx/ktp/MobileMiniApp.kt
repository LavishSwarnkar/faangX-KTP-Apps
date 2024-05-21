package com.faangx.ktp

import androidx.compose.runtime.Composable

class MobileMiniApp<T>(
    val label: String,
    val functionalityClass: Class<T>,
    val functionalityInterface: String,
    val functionalityImpl: String,
    val functionalityFuns: String,
    val functionalityImplClassName: String,
    val testClass: Class<*>?,
    val packageName: String,
    val composable: @Composable (T) -> Unit
)

fun <T> MobileMiniApp<T>.simpleName(): String {
    return label.replace(" ", "")
        .replace("&", "n")
}