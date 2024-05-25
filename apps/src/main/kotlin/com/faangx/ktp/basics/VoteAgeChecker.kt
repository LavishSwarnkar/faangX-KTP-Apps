package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import com.faangx.ktp.comp.DynamicRowColumn
import com.faangx.ktp.comp.HighlightedText
import ksp.MiniApp

@MiniApp(
    name = "Vote Age Checker",
    repoPath = "ProgrammingFundamentals/Ep2/VoteAgeChecker",
    paramNames = "age"
)
@Composable
fun VoteAgeChecker(
    canVote: (Int) -> Boolean
) {
    var num by remember { mutableStateOf("") }
    val result = derivedStateOf {
        val eligible = num.toIntOrNull()?.run(canVote)
            ?: return@derivedStateOf SMILE_EMOJI
        if (eligible) "CAN Vote" else "CAN'T Vote"
    }

    DynamicRowColumn(
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Person with age",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            label = { Text("years") },
            value = num,
            onValueChange = { if (it.length <= 5) num = it },
            textStyle = MaterialTheme.typography.titleLarge
        )

        HighlightedText(
            text = " ${result.value} ",
            style = MaterialTheme.typography.titleLarge
        )
    }
}