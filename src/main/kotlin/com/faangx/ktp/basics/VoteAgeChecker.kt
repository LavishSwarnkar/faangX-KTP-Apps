package com.faangx.ktp.basics

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI

fun VoteAgeCheckerMiniApp(
    canVote: (Int) -> Boolean
) {
    MiniApp(
        title = "Vote Age Checker",
        composable = {
            VoteAgeChecker(canVote)
        }
    )
}

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

    Row (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Person with age  ",
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            value = num,
            onValueChange = { if (it.length <= 5) num = it },
            textStyle = MaterialTheme.typography.h5
        )

        Text(
            text = "  years",
            style = MaterialTheme.typography.h5
        )

        Text(
            text = " ${result.value} ",
            style = MaterialTheme.typography.h4
        )
    }
}