package com.faangx.ktp.num_pad

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key.Companion.Backspace
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.faangx.ktp.num_pad.NumPadConstants.buttons
import com.faangx.ktp.num_pad.NumPadConstants.numButtonSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumPad(
    // Required to collect HW Keyboard key press events
    keyFlow: MutableSharedFlow<Char>? = null
) {
    var input by remember { mutableStateOf("") }

    val interactionSources = remember {
        buildList { repeat(10) { add(MutableInteractionSource()) } }
    }

    val numButtonPxSize = with(LocalDensity.current) { remember { numButtonSize.toPx() } }

    keyFlow?.let {
        LaunchedEffect(Unit) {
            keyFlow.collect { char ->

                if (char == Char(Backspace.nativeKeyCode)) {
                    input = input.dropLast(1)
                    return@collect
                }

                val index = ((char.code - '0'.code) + 9) % 10
                val interactionSource = interactionSources[index]

                input += buttons[index].num

                val press = PressInteraction.Press(
                    Offset(numButtonPxSize / 2, numButtonPxSize / 2)
                )
                interactionSource.emit(press)
                delay(50)
                interactionSource.emit(PressInteraction.Release(press))
            }
        }
    }

    Column(
        Modifier
            .wrapContentSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.width(numButtonSize * 3),
            value = input,
            onValueChange = { },
            enabled = false,
            trailingIcon = {
                IconButton(
                    onClick = { input = input.dropLast(1) }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Backspace")
                }
            },
            textStyle = MaterialTheme.typography.h6,
            maxLines = 1
        )

        Spacer(Modifier.size(16.dp))

        LazyVerticalGrid(
            modifier = Modifier.size(
                width = numButtonSize * 3,
                height = numButtonSize * 4
            ),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(
                items = buttons,
                span = { index, _ ->
                    GridItemSpan(
                        if (index == buttons.lastIndex) 3 else 1
                    )
                }
            ) { index, numButton ->
                NumButtonComp(
                    numButton = numButton,
                    modifier = if (index == buttons.lastIndex) {
                        Modifier.padding(horizontal = numButtonSize)
                    } else {
                        Modifier
                    },
                    interactionSource = interactionSources[index],
                    onClick = {
                        input += numButton.num
                    }
                )
            }
        }
    }
}