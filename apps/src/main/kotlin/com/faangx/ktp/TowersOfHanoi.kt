package com.faangx.ktp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

class Towers(
    noOfDiscs: Int
) {
    val towers = listOf(
        Stack<Int>().apply {
            for (i in noOfDiscs downTo 1) push(i)
        },
        Stack(),
        Stack()
    )

    fun apply(step: Step) {
        val (src, dest) = step
        towers[dest].push(
            towers[src].pop()
        )
    }

    override fun toString(): String {
        return buildString {
            append("A : ${towers[0].stringify()}\n")
            append("B : ${towers[1].stringify()}\n")
            append("C : ${towers[2].stringify()}\n\n")
        }
    }
}

fun Stack<Int>.stringify(): String {
    return toArray().joinToString(" -> ")
}

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

fun main() {
        App()
//    println(getIntermediateStates(3))

//    val steps = getStepsFor(3)
//    val towers = Towers(3)
//    println(towers)
//    steps.forEach { step ->
//        towers.apply(step)
//        println(towers)
//    }
}

fun getIntermediateStates(noOfDiscs: Int): List<Towers> {
    return buildList {
        val steps = getStepsFor(noOfDiscs)
        add(Towers(noOfDiscs))
        for (i in steps.indices) {
            val towers = Towers(noOfDiscs)
            steps.subList(0, i + 1).forEach { towers.apply(it) }
            add(towers)
        }
    }
}

fun App(noOfDiscs: Int = 3) {
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
                getIntermediateStates(noOfDiscs)
            }

            val towers = remember {
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
                    towers.value.towers.forEachIndexed { i, el ->
                        Tower("${Char('A'.code + i)}", el, noOfDiscs)
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
                            if (i > 0) towers.value = steps[--i]
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
                            if(i < steps.size - 1) towers.value = steps[++i]
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
fun RowScope.Tower(label: String, tower: Stack<Int>, noOfDiscs: Int) {

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement
            .spacedBy(12.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(12.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement
                    .spacedBy(12.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                tower.toList().reversed().forEach { discNo: Int ->
                    Disc(discNo, noOfDiscs)
                }
            }
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
fun Disc(discNo: Int, noOfDiscs: Int) {
    val widthFractionStart = 0.2f

    val step = (1 - widthFractionStart) / noOfDiscs

    Box(
        modifier = Modifier.fillMaxWidth(widthFractionStart + (discNo * step))
            .height(60.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Text(
            text = "$discNo",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
    }
}
