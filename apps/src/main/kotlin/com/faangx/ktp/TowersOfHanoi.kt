package com.faangx.ktp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
import java.util.Stack

typealias Step = Pair<Int, Int>

private fun towersOfHanoi(n: Int, src: Int, dest: Int, aux: Int, steps: MutableList<Step>) {
    if (n == 0) return
    // Move n-1 disks from src to aux
    towersOfHanoi(n - 1, src, aux, dest, steps)

    steps.add(src to dest)

    // Move the n-1 disks from aux to dest
    towersOfHanoi(n - 1, aux, dest, src, steps)
}

private fun getStepsFor(noOfDiscs: Int): List<Step> {
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
//    App()
//    println(getIntermediateStates(3))
    stack()
}

fun stack() {
    val stack = Stack<Int>()
    stack.push(5)
    stack.push(3)
    println(stack.pop())
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
            var i by remember { mutableStateOf(0) }

            val steps = remember {
                getIntermediateStates(5)
            }

            val rods = remember {
                mutableStateOf(
                    steps[0]
                )
            }

//            LaunchedEffect(Unit) {
//                repeat(steps.size - 1) {
//                    delay(700)
//                    rods.value = steps[++i]
//                }
//            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    rods.value.forEachIndexed { i, el ->
                        Rod("${Char('A'.code + i)}", el)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow)
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    IconButton(
                        onClick = {
                            if (i > 0) rods.value = steps[--i]
                        }
                    ) {
                        Text(
                            text = "«",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black,
                                lineHeight = 30.sp
                            ),
                            color = Color.Black
                        )
                    }

                    Text(
                        text = "Step ${i + 1}/${steps.size}",
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    IconButton(
                        onClick = {
                            if(i < steps.size - 1) rods.value = steps[++i]
                        }
                    ) {
                        Text(
                            text = "»",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black
                            ),
                            color = Color.Black
                        )
                    }
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
