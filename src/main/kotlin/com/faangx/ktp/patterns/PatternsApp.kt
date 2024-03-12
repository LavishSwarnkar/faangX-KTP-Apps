package com.faangx.ktp.patterns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.faangx.ktp.patterns.comp.PatternCard
import java.io.ByteArrayOutputStream

typealias PrintPatternFunctionality = (
    patternNo: Int,
    lines: Int,
    customization: String,
    stream: ByteArrayOutputStream
) -> Unit

typealias GetDefaultCustomization = (
    patternNo: Int
) -> String?

@Composable
fun PatternsApp(
    printPattern: PrintPatternFunctionality,
    getDefaultCustomization: GetDefaultCustomization
) {
    var selectedPattern by remember { mutableStateOf(0) }
    var lines by remember { mutableStateOf("5") }
    var customization by remember { mutableStateOf("*") }
    var pattern by remember { mutableStateOf("") }

    fun updatePattern() {
        val stream = ByteArrayOutputStream()
        printPattern(
            selectedPattern + 1,
            lines.toIntOrNull() ?: return,
            customization,
            stream
        )
        pattern = stream.toString()
    }

    LaunchedEffect(Unit) {
        updatePattern()
    }

    MaterialTheme {
        Column(
            Modifier.fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(getPatterns()) { index, pattern ->
                    PatternCard(
                        patternNo = index + 1,
                        patternSample = pattern,
                        selected = selectedPattern == index,
                        onClick = {
                            selectedPattern = index
                            customization = getDefaultCustomization(index + 1) ?: ""
                            updatePattern()
                        }
                    )
                }
            }
            Spacer(Modifier.size(30.dp))

            Row(
                Modifier.width(300.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    label = { Text("Lines") },
                    value = lines,
                    onValueChange = {
                        lines = it
                        updatePattern()
                    }
                )

                OutlinedTextField(
                    modifier = Modifier.weight(2f),
                    label = { Text("Customization") },
                    value = customization,
                    onValueChange = {
                        customization = it
                        updatePattern()
                    }
                )
            }
            Spacer(Modifier.size(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = pattern,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}