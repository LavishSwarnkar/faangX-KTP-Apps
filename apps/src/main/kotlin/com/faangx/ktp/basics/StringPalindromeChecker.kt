package com.faangx.ktp.basics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
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
import com.streamliners.compose.comp.select.LabelledCheckBox

fun StringPalindromeCheckerMiniApp(
    reverseStr: (String) -> String,
    isPalindrome: (String, Boolean) -> Boolean
) {
    MiniApp(
        title = "String Palindrome Checker",
        composable = {
            StringPalindromeChecker(reverseStr, isPalindrome)
        }
    )
}

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
        ) {
            OutlinedTextField(
                modifier = Modifier.width(280.dp),
                value = str,
                onValueChange = { if (it.length <= 15) str = it },
                textStyle = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.End
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = if (palindrome.value)
                        LIGHT_GREEN else Color.Transparent
                )
            )

            VerticalDivider(
                modifier = Modifier.fillMaxHeight()
                    .padding(horizontal = 24.dp)
            )

            OutlinedTextField(
                modifier = Modifier.width(280.dp),
                value = reverse.value,
                readOnly = true,
                onValueChange = { },
                textStyle = MaterialTheme.typography.h5,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = if (palindrome.value)
                        LIGHT_GREEN else Color.Transparent
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