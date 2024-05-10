package com.faangx.ktp.num_pad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.faangx.ktp.num_pad.NumPadConstants.numButtonNumStyle
import com.faangx.ktp.num_pad.NumPadConstants.numButtonSpacerSize
import com.faangx.ktp.num_pad.NumPadConstants.numButtonSize
import com.faangx.ktp.num_pad.NumPadConstants.numButtonSubtitleStyle

class NumButton(
    val num: Char,
    val subtitle: String = "-"
)

@Composable
fun NumButtonComp(
    numButton: NumButton,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier.size(numButtonSize)
            .clip(CircleShape),
        onClick = onClick,
        interactionSource = interactionSource,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.Black
        )
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${numButton.num}",
                style = numButtonNumStyle()
            )

            Spacer(Modifier.size(numButtonSpacerSize))

            Text(
                text = numButton.subtitle,
                style = numButtonSubtitleStyle()
            )
        }
    }

}