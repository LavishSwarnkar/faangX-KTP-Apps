package com.faangx.ktp.basics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.basics.Quantity.*
import com.faangx.ktp.comp.*
import ksp.MiniApp
import kotlin.math.abs

private enum class Quantity { CP, PL, AbsPL, SP }
private enum class PorL { Profit, Loss }

fun ProfitLossCalculatorMiniApp(
    getSp1: (cp: Int, pl: Int) -> Int,
    getSp2: (cp: Int, absPL: Int) -> Int,

    getCp1: (sp: Int, pl: Int) -> Int,
    getCp2: (sp: Int, absPL: Int) -> Int,

    getPl1: (cp: Int, sp: Int) -> Int,
    getPl2: (cp: Int, absPL: Int) -> Int,
    getPl3: (sp: Int, absPL: Int) -> Int,

    getAbsPL1: (cp: Int, sp: Int) -> Int,
    getAbsPL2: (cp: Int, pl: Int) -> Int,
    getAbsPL3: (sp: Int, pl: Int) -> Int,
) {
    MiniApp(
        title = "Profit & Loss Calculator",
        composable = {
            ProfitLossCalculator(
                getSp1, getSp2, getCp1, getCp2, getPl1, getPl2, getPl3, getAbsPL1, getAbsPL2, getAbsPL3
            )
        }
    )
}

@MiniApp(
    "Profit & Loss Calculator",
    "cp, pl; cp, absPL; sp, pl; sp, absPL; cp, sp; cp, absPL; sp, absPL; cp, sp; cp, pl; sp, pl"
)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfitLossCalculator(
    getSp1: (cp: Int, pl: Int) -> Int,
    getSp2: (cp: Int, absPL: Int) -> Int,

    getCp1: (sp: Int, pl: Int) -> Int,
    getCp2: (sp: Int, absPL: Int) -> Int,

    getPl1: (cp: Int, sp: Int) -> Int,
    getPl2: (cp: Int, absPL: Int) -> Int,
    getPl3: (sp: Int, absPL: Int) -> Int,

    getAbsPL1: (cp: Int, sp: Int) -> Int,
    getAbsPL2: (cp: Int, pl: Int) -> Int,
    getAbsPL3: (sp: Int, pl: Int) -> Int,
) {
    val knownQuantities = remember { mutableStateListOf<Quantity>() }

    val cp = remember { mutableStateOf("") }
    val pl = remember { mutableStateOf("") }
    val absPL = remember { mutableStateOf("") }
    val sp = remember { mutableStateOf("") }
    val pORl = remember { mutableStateOf<PorL?>(null) }

    val screenSize = rememberScreenSize()

    val showCheckBoxes = remember { mutableStateOf(screenSize.iz(ScreenSize.Small)) }

    LaunchedEffect(cp.value, sp.value, pl.value, absPL.value, pORl.value) {
        when (knownQuantities.toSet()) {
            setOf(CP, PL) -> {
                if (pORl.value == null) return@LaunchedEffect
                val cp_ = cp.value.toIntOrNull() ?: return@LaunchedEffect
                var pl_ = pl.value.toIntOrNull() ?: return@LaunchedEffect
                if (pORl.value == PorL.Loss) pl_ *= -1

                absPL.value = abs(getAbsPL2(cp_, pl_)).toString()
                sp.value = getSp1(cp_, pl_).toString()
            }
            setOf(CP, AbsPL) -> {
                if (pORl.value == null) return@LaunchedEffect
                val cp_ = cp.value.toIntOrNull() ?: return@LaunchedEffect
                var absPL_ = absPL.value.toIntOrNull() ?: return@LaunchedEffect
                if (pORl.value == PorL.Loss) absPL_ *= -1

                pl.value = abs(getPl2(cp_, absPL_)).toString()
                sp.value = getSp2(cp_, absPL_).toString()
            }
            setOf(SP, PL) -> {
                if (pORl.value == null) return@LaunchedEffect
                val sp_ = sp.value.toIntOrNull() ?: return@LaunchedEffect
                var pl_ = pl.value.toIntOrNull() ?: return@LaunchedEffect
                if (pORl.value == PorL.Loss) pl_ *= -1

                absPL.value = abs(getAbsPL3(sp_, pl_)).toString()
                cp.value = getCp1(sp_, pl_).toString()
            }
            setOf(SP, AbsPL) -> {
                if (pORl.value == null) return@LaunchedEffect
                val sp_ = sp.value.toIntOrNull() ?: return@LaunchedEffect
                var absPL_ = absPL.value.toIntOrNull() ?: return@LaunchedEffect
                if (pORl.value == PorL.Loss) absPL_ *= -1

                pl.value = abs(getPl3(sp_, absPL_)).toString()
                cp.value = getCp2(sp_, absPL_).toString()
            }
            setOf(CP, SP) -> {
                val cp_ = cp.value.toIntOrNull() ?: return@LaunchedEffect
                val sp_ = sp.value.toIntOrNull() ?: return@LaunchedEffect

                val absPL_ = getAbsPL1(cp_, sp_)

                pl.value = abs(getPl1(cp_, sp_)).toString()
                absPL.value = abs(absPL_).toString()

                pORl.value = if (absPL_ >= 0) PorL.Profit else PorL.Loss
            }
        }
    }

    val onQuantityCheckChanged = { qty: Quantity, isChecked: Boolean ->
        if (isChecked) {
            if (qty == PL && knownQuantities.contains(AbsPL)) knownQuantities.remove(AbsPL)
            if (qty == AbsPL && knownQuantities.contains(PL)) knownQuantities.remove(PL)
            if (knownQuantities.size == 2) knownQuantities.removeAt(0)
        }

        if (isChecked) {
            knownQuantities.add(qty)
        } else {
            knownQuantities.remove(qty)
        }; Unit
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DynamicRowColumn(
            Modifier.run {
                 if (screenSize.iz(ScreenSize.Large)) {
                     showCheckBoxes.value = false
                     onPointerEvent(PointerEventType.Enter) { showCheckBoxes.value = true }
                         .onPointerEvent(PointerEventType.Exit) { showCheckBoxes.value = false }
                 } else {
                     showCheckBoxes.value = true
                     this
                 }
            },
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OptionalTextField(
                isChecked = knownQuantities.contains(CP),
                onCheckChanged = { onQuantityCheckChanged(CP, it) },
                hint = "CP",
                input = cp,
                showCheckBoxes = showCheckBoxes
            )

            Text(
                modifier = Modifier.padding(bottom = 24.dp)
                    .centerAlign(this),
                text = "+",
                style = MaterialTheme.typography.headlineMedium
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    OptionalTextField(
                        isChecked = knownQuantities.contains(PL),
                        onCheckChanged = { onQuantityCheckChanged(PL, it) },
                        hint = pORl.value?.name?.let { "$it %" } ?: "P/L %",
                        input = pl,
                        showCheckBoxes = showCheckBoxes
                    )

                    OptionalTextField(
                        isChecked = knownQuantities.contains(AbsPL),
                        onCheckChanged = { onQuantityCheckChanged(AbsPL, it) },
                        hint = pORl.value?.name ?: "P/L",
                        input = absPL,
                        showCheckBoxes = showCheckBoxes
                    )
                }

                RadioGroup(
                    state = pORl,
                    options = PorL.entries.toList(),
                    labelExtractor = { it.name },
                    layout = Layout.Row,
                    enabled = knownQuantities.containsAnyOf(PL, AbsPL)
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 24.dp)
                    .centerAlign(this),
                text = "=",
                style = MaterialTheme.typography.headlineMedium
            )

            OptionalTextField(
                isChecked = knownQuantities.contains(SP),
                onCheckChanged = { onQuantityCheckChanged(SP, it) },
                hint = "SP",
                input = sp,
                showCheckBoxes = showCheckBoxes
            )
        }
    }
}

@Composable
private fun OptionalTextField(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    hint: String,
    input: MutableState<String>,
    showCheckBoxes: MutableState<Boolean>
) {
    Column(
        modifier.width(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onCheckChanged(!isChecked) }
                .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (Modifier.size(20.dp)) {
            AnimatedVisibility(showCheckBoxes.value) {
                Checkbox(isChecked, onCheckChanged)
            }
        }

        OutlinedTextField(
            label = { Text(hint) },
            value = input.value,
            onValueChange = { input.value = it },
            enabled = isChecked,
            textStyle = MaterialTheme.typography.titleLarge,
            singleLine = true,
            maxLines = 1
        )
    }
}