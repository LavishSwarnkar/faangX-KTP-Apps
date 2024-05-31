package com.faangx.ktp.basics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faangx.ktp.LIGHT_GREEN
import com.streamliners.compose.comp.select.LabelledCheckBox
import ksp.MiniApp

@MiniApp(
    "String Palindrome Checker",
    "ProgrammingFundamentals/Ep4/StringPalindrome",
    "str; str, ignoreCase"
)
@Composable
fun StringPalindromeChecker(
    reverseStr: (String) -> String,
    isPalindrome: (String, Boolean) -> Boolean
) {
    var str by remember { mutableStateOf("") }
    var ignoreCase by remember { mutableStateOf(false) }
    val reverse = derivedStateOf { reverseStr(str) }
    val palindrome = derivedStateOf { isPalindrome(str, ignoreCase) }

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
                value = str,
                onValueChange = { if (it.length <= 15) str = it },
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
                    .padding(horizontal = 24.dp)
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

        LabelledCheckBox(
            modifier = Modifier.padding(top = 16.dp),
            label = "Ignore case",
            checked = ignoreCase,
            onToggle = { ignoreCase = !ignoreCase },
            labelStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge
        )

        AnimatedVisibility(palindrome.value) {
            LabelledCheckBox(
                modifier = Modifier.padding(top = 16.dp),
                label = "Palindrome",
                checked = true,
                onToggle = {},
                labelStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge
            )
        }
    }
}