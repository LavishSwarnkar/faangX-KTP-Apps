package com.faangx.ktp.num_pad

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object NumPadConstants {

    val numButtonSize = 100.dp
    @Composable fun numButtonNumStyle() = MaterialTheme.typography.h4
    @Composable fun numButtonSubtitleStyle() = MaterialTheme.typography.body2
    val numButtonSpacerSize = 8.dp

    val buttons = listOf(
        NumButton('1'),
        NumButton('2', "ABC"),
        NumButton('3', "DEF"),
        NumButton('4', "GHI"),
        NumButton('5', "JKL"),
        NumButton('6', "MNO"),
        NumButton('7', "PQRS"),
        NumButton('8', "TUV"),
        NumButton('9', "WXYZ"),
        NumButton('0', "WXYZ")
    )
}