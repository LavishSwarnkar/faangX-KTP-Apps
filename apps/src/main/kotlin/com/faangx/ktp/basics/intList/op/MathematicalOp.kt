package com.faangx.ktp.basics.intList.op

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.faangx.ktp.basics.intList.model.IntListOp
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import com.streamliners.utils.safeLet

class MathematicalOp(
    val findMin: (List<Int>) -> Int?,
    val findMax: (List<Int>) -> Int?,
    val calculateSum: (List<Int>) -> Int?,
    val calculateMean: (List<Int>) -> Float?
) : IntListOp {
    override val label: String = "Mathematical"
    override val Composable: @Composable (List<Int>) -> Unit = { Comp(it) }
}

data class IntListMathOpsResult(
    val min: Int? = null,
    val max: Int? = null,
    val sum: Int? = null,
    val mean: Float? = null
)

@Composable
fun MathematicalOp.Comp(list: List<Int>) {
    val result = remember { mutableStateOf(IntListMathOpsResult()) }

    LaunchedEffect(list) {
        result.value = IntListMathOpsResult(
            min = findMin(list),
            max = findMax(list),
            sum = calculateSum(list),
            mean = calculateMean(list)
        )
    }

    result.value.run {
        safeLet(min, max, sum, mean) { min, max, sum, mean ->
            DynamicRowColumn(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                IntListOpsResultElement("Min", min.toString())
                IntListOpsResultElement("Max", max.toString())
                IntListOpsResultElement("Sum", sum.toString())
                IntListOpsResultElement("Mean", "%.2f".format(mean))
            }
        }
    }
}

@Composable
private fun IntListOpsResultElement(
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label =",
            style = MaterialTheme.typography.titleLarge
        )
        HighlightedText(
            text = value
        )
    }
}