package com.faangx.ktp.basics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.LIGHT_GREEN
import com.faangx.ktp.MiniApp
import com.faangx.ktp.SMILE_EMOJI
import ksp.MiniApp

@MiniApp(
    "Number Palindrome Checker",
    "ProgrammingFundamentals/Ep4/NumberPalindrome",
    "num; num"
)
@Composable
fun NumberPalindromeChecker(
    reverseNum: (Long) -> Long,
    isPalindrome: (Long) -> Boolean
) {
    var num by remember { mutableStateOf("") }
    val reverse = derivedStateOf {
        num.toLongOrNull()?.run(reverseNum)?.toString() ?: ""
    }
    val palindrome = derivedStateOf {
        num.toLongOrNull()?.run(isPalindrome) ?: false
    }

    Column (
        Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
                .widthIn(max = 600.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = num,
                onValueChange = { if (it.length <= 15) num = it },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.End
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (palindrome.value) LIGHT_GREEN else Color.Transparent,
                    unfocusedContainerColor = if (palindrome.value) LIGHT_GREEN else Color.Transparent
                )
            )

            VerticalDivider(
                modifier = Modifier.fillMaxHeight()
                    .padding(horizontal = 12.dp)
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = reverse.value,
                readOnly = true,
                onValueChange = { },
                textStyle = MaterialTheme.typography.titleLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (palindrome.value) LIGHT_GREEN else Color.Transparent,
                    unfocusedContainerColor = if (palindrome.value) LIGHT_GREEN else Color.Transparent
                )
            )
        }

        AnimatedVisibility(palindrome.value) {
            LabelledCheckBox(
                modifier = Modifier.padding(top = 16.dp),
                label = "Palindrome",
                checked = true,
                onToggle = {},
                labelStyle = MaterialTheme.typography.titleLarge
            )
        }
    }
}