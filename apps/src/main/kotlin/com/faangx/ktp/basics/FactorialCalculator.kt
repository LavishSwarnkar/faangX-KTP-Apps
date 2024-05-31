package com.faangx.ktp.basics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.basics.Operation.*
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import com.streamliners.utils.safeLet
import ksp.MiniApp

enum class Operation {
    Permutations, Combinations
}

@MiniApp(
    name = "Factorial Calculator",
    repoPath = "ProgrammingFundamentals/Ep3/FactorialCalculator",
    paramNames = "num; n, r; n, r"
)
@Composable
fun FactorialCalculatorApp(
    factorialOf: (Long) -> Long,
    permutationsOf: (n: Long, r: Long) -> Long,
    combinationsOf: (n: Long, r: Long) -> Long
) {
    val x = remember { mutableStateOf("") }
    val factorial = remember {
        derivedStateOf {
            val num = x.value.toLongOrNull() ?: return@derivedStateOf SMILE_EMOJI
            factorialOf(num).toString()
        }
    }

    val n = remember { mutableStateOf("") }
    val r = remember { mutableStateOf("") }
    val operation = remember { mutableStateOf<Operation?>(Permutations) }

    val operationResult = remember {
        derivedStateOf {
            safeLet(
                n.value.toLongOrNull(),
                r.value.toLongOrNull(),
                operation.value
            ) { n_, r_, operation_ ->

                when (operation_) {
                    Permutations -> permutationsOf(n_, r_)
                    Combinations -> combinationsOf(n_, r_)
                }.toString()

            } ?: SMILE_EMOJI
        }
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        DynamicRowColumn(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.width(80.dp),
                    value = x.value,
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    onValueChange = { if (it.length <= 2) x.value = it }
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    text = "!   =",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            HighlightedText(
                text = factorial.value,
                style = MaterialTheme.typography.titleLarge
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DynamicRowColumn(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    OutlinedTextField(
                        modifier = Modifier.width(80.dp),
                        value = n.value,
                        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.End),
                        onValueChange = { if (it.length <= 2) n.value = it }
                    )

                    Text(
                        modifier = Modifier.padding(top = 30.dp, start = 8.dp).width(40.dp),
                        text = if (operation.value == Permutations) "P" else "C",
                        fontSize = 60.sp,
                        textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        modifier = Modifier.padding(top = 80.dp, start = 8.dp).width(80.dp),
                        value = r.value,
                        textStyle = MaterialTheme.typography.titleLarge,
                        onValueChange = { if (it.length <= 2) r.value = it }
                    )
                }

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "=",
                    style = MaterialTheme.typography.titleLarge
                )

                HighlightedText(
                    text = operationResult.value,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(Modifier.size(20.dp))

            RadioGroup(
                state = operation,
                options = Operation.entries.toList(),
                labelExtractor = { it.name }
            )
        }
    }
}