package com.faangx.ktp.basics.intList.model

import androidx.compose.runtime.Composable

interface IntListOp {
    val label: String
    val Composable: @Composable (MutableList<Int>) -> Unit
}