package com.faangx.ktp

import java.util.Stack

class Towers(
    noOfDiscs: Int
) {
    val srcTower: Stack<Int> = Stack<Int>().apply {
        for (i in noOfDiscs..1) push(i)
    }

    val destTower: Stack<Int> = Stack()
    val auxTower: Stack<Int> = Stack()
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

