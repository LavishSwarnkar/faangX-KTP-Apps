package com.faangx.ktp.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.DynamicRowColumn
import ksp.MiniApp

fun GradeCalculatorMiniApp(
    getGrade: (Int, Int, Int, Int, Int) -> String
) {
    MiniApp(
        title = "Grade Calculator",
        composable = {
            GradeCalculatorApp(getGrade)
        }
    )
}

@MiniApp("Grade Calculator")
@Composable
fun GradeCalculatorApp(
    getGrade: (Int, Int, Int, Int, Int) -> String
) {
    val marks1 = remember { mutableStateOf("") }
    val marks2 = remember { mutableStateOf("") }
    val marks3 = remember { mutableStateOf("") }
    val marks4 = remember { mutableStateOf("") }
    val marks5 = remember { mutableStateOf("") }

    val grade = derivedStateOf {
        val marks1Int = marks1.value.toIntOrNull()
            ?: return@derivedStateOf SMILE_EMOJI
        val marks2Int = marks2.value.toIntOrNull()
            ?: return@derivedStateOf SMILE_EMOJI
        val marks3Int = marks3.value.toIntOrNull()
            ?: return@derivedStateOf SMILE_EMOJI
        val marks4Int = marks4.value.toIntOrNull()
            ?: return@derivedStateOf SMILE_EMOJI
        val marks5Int = marks5.value.toIntOrNull()
            ?: return@derivedStateOf SMILE_EMOJI

        getGrade(marks1Int, marks2Int, marks3Int, marks4Int, marks5Int)
    }

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Student with marks",
            style = MaterialTheme.typography.h5
        )

        DynamicRowColumn(
            Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf(marks1, marks2).forEach { state ->
                    OutlinedTextField(
                        modifier = Modifier.width(80.dp),
                        value = state.value,
                        textStyle = MaterialTheme.typography.h5,
                        onValueChange = { if (it.length <=3) state.value = it }
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf(marks3, marks4).forEach { state ->
                    OutlinedTextField(
                        modifier = Modifier.width(80.dp),
                        value = state.value,
                        textStyle = MaterialTheme.typography.h5,
                        onValueChange = { if (it.length <=3) state.value = it }
                    )
                }
            }

            OutlinedTextField(
                modifier = Modifier.width(80.dp),
                value = marks5.value,
                textStyle = MaterialTheme.typography.h5,
                onValueChange = { if (it.length <=3) marks5.value = it }
            )
        }

        Text(
            text = "/ 100 has grade",
            style = MaterialTheme.typography.h5
        )

        Text(
            modifier = Modifier.padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.surface)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = grade.value,
            style = MaterialTheme.typography.h3
        )
    }
}

fun main() {

}