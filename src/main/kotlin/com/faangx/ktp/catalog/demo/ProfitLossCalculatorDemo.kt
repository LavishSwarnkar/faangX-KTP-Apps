package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.ProfitLossCalculator
import kotlin.math.roundToInt

@Composable
fun ProfitLossCalculatorDemo() {
    ProfitLossCalculator(
        getSp1 = { cp, pl ->
            (cp * ((100 + pl) / 100f)).roundToInt()
        },
        getSp2 = { cp, absPL ->
            cp + absPL
        },
        getCp1 = { sp, pl ->
            (sp * (100f / (100 + pl))).roundToInt()
        },
        getCp2 = { sp, absPL ->
            sp - absPL
        },
        getPl1 = { cp, sp ->
            (((sp - cp) / cp.toFloat()) * 100).roundToInt()
        },
        getPl2 = { cp, absPL ->
            (absPL / cp.toFloat() * 100).roundToInt()
        },
        getPl3 = { sp, absPL ->
            (absPL / (sp - absPL).toFloat() * 100).roundToInt()
        },
        getAbsPL1 = { cp, sp ->
            sp - cp
        },
        getAbsPL2 = { cp, pl ->
            (cp * (pl / 100f)).roundToInt()
        },
        getAbsPL3 = { sp, pl ->
            ((sp * pl) / (100f + pl)).roundToInt()
        },
    )
}