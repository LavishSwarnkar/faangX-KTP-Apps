package com.faangx.ktp.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.faangx.ktp.catalog.comp.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniAppsCatalog() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val selectedApp = remember { mutableStateOf(MiniApp.SquareOfNum) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(selectedApp) }
    ) {
        Scaffold(
            topBar = {
                val scope = rememberCoroutineScope()

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
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                selectedApp.value.demo()
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Stages of life"
    ) {
        MaterialTheme {
            MiniAppsCatalog()
        }
    }
}