package com.faangx.ktp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay

typealias Step = Pair<Int, Int>

fun towersOfHanoi(n: Int, src: Int, dest: Int, aux: Int, steps: MutableList<Step>) {
    if (n == 0) return
    // Move n-1 disks from src to aux
    towersOfHanoi(n - 1, src, aux, dest, steps)

    steps.add(src to dest)

    // Move the n-1 disks from aux to dest
    towersOfHanoi(n - 1, aux, dest, src, steps)
}

fun getStepsFor(noOfDiscs: Int): List<Step> {
    val steps = mutableListOf<Step>()
    towersOfHanoi(noOfDiscs, 0, 1, 2, steps)
    return steps
}

fun getIntermediateStates(noOfDiscs: Int): List<List<Int>> {
    return buildList {

        var rods = listOf(noOfDiscs, 0, 0)
        add(rods)
        val steps = getStepsFor(noOfDiscs)
        steps.forEach { (src, dest) ->
            rods = rods.mapIndexed { i, el ->
                when (i) {
                    src -> el - 1
                    dest -> el + 1
                    else -> el
                }
            }
            add(rods)
        }
    }
}

fun main() {
    App()
//    println(getIntermediateStates(3))
}

fun App() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TowersOfHanoi",
            state = rememberWindowState(
                size = DpSize(1000.dp, 700.dp)
            )
        ) {
            var i = 0

            val steps = remember {
                getIntermediateStates(5)
            }

            val rods = remember {
                mutableStateOf(
                    steps[0]
                )
            }

            LaunchedEffect(Unit) {
                repeat(steps.size - 1) {
                    delay(700)
                    rods.value = steps[++i]
                }
            }

            Row(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black),
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                rods.value.forEachIndexed { i, el ->
                    Rod("${Char('A'.code + i)}", el)
                }
            }
        }
    }
}

@Composable
fun RowScope.Rod(label: String, noOfDiscs: Int) {

    val widthFractionStart = 0.2f

    val step = (1 - widthFractionStart) / noOfDiscs

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement
            .spacedBy(12.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(noOfDiscs) {
            Disc(widthFractionStart + ((it + 1) * step))
        }

        Text(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}

@Composable
fun Disc(widthFraction: Float) {
    Box(
        modifier = Modifier.fillMaxWidth(widthFraction)
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    )
}
