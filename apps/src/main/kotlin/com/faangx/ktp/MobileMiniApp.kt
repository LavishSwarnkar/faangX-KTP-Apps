package com.faangx.ktp

import androidx.compose.runtime.Composable

class MobileMiniApp<T>(
    val label: String,
    val functionalityClass: Class<T>,
    val functionalityInterface: String,
    val functionalityImpl: String,
    val functionalityFuns: String,
    val functionalityImplClassName: String,
    val packageName: String,
    private val composable: @Composable (T) -> Unit
)