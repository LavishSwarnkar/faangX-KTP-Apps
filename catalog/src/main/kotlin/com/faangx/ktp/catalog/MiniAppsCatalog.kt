package com.faangx.ktp.catalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.faangx.ktp.catalog.comp.MiniAppsMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniAppsCatalog() {

    var menuVisible by remember { mutableStateOf(true) }
    val selectedApp = remember { mutableStateOf(MiniApp.SquareOfNum) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Mini Apps Catalog",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { menuVisible = !menuVisible }
                    ) {
                        val rotation = remember { derivedStateOf { if (menuVisible) 0f else 180f } }
                        val animatedRotation = animateFloatAsState(rotation.value)
                        Icon(
                            modifier = Modifier.rotate(animatedRotation.value),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Row (
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(menuVisible) {
                MiniAppsMenu(selectedApp)
            }

            MiniAppComp(
                modifier = Modifier.weight(1f),
                title = selectedApp.value.title,
                composable = selectedApp.value.demo
            )
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Mini Apps Catalog",
        state = rememberWindowState(size = DpSize(1200.dp, 700.dp))
    ) {
        MaterialTheme {
            MiniAppsCatalog()
        }
    }
}