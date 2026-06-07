package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.AtmOutput
import com.faangx.ktp.basics.AtmSimulator
import com.faangx.ktp.basics.Denomination
import com.faangx.ktp.basics.Denomination.Companion.INRDenominations

fun getAtmOutput(
    amount: Int,
    currencyDenoms: List<Denomination>
): AtmOutput {
    return buildMap {

        var amountLeft = amount

        for (denom in currencyDenoms) {
            when {
                amountLeft >= denom.value -> {
                    val qty = amountLeft / denom.value
                    amountLeft -= qty * denom.value
                    put(denom, qty)
                }
                amountLeft == 0 -> break
            }
        }
    }
}

@Composable
fun AtmSimulatorDemo() {
    AtmSimulator(
        getAtmOutput = { getAtmOutput(it, INRDenominations) }
    )
}