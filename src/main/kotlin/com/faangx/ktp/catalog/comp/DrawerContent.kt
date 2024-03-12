package com.faangx.ktp.catalog.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faangx.ktp.catalog.MiniApp
import com.faangx.ktp.catalog.groupedByCategory
import com.faangx.ktp.catalog.groupedBySubcategory

@Composable
fun DrawerContent(
    selection: MutableState<MiniApp>
) {
    ModalDrawerSheet {
        Text(
            text = "Mini apps catalog",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalDivider()

        val categories = remember { MiniApp.groupedByCategory() }

        categories.forEach { (category, apps) ->
            CategoryCard(category, apps, selection)
        }
    }
}

@Composable
private fun CategoryCard(
    category: String,
    apps: List<MiniApp>,
    selection: MutableState<MiniApp>
) {
    ElevatedCard {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(4.dp),
        ) {
            Text(
                category,
                style = MaterialTheme.typography.titleMedium
            )

            val subcategories = remember { apps.groupedBySubcategory() }

            subcategories.forEach { (subcategory, apps) ->
                SubcategoryCard(
                    Modifier.padding(start = 8.dp),
                    subcategory, apps, selection
                )
            }
        }
    }
}

@Composable
private fun SubcategoryCard(
    modifier: Modifier,
    subcategory: String,
    apps: List<MiniApp>,
    selection: MutableState<MiniApp>
) {
    Column(
        modifier = modifier,
    ) {
        if (subcategory != "NA") {
            Text(
                subcategory,
                style = MaterialTheme.typography.titleSmall
            )
        }

        apps.forEach { app ->
            NavigationDrawerItem(
                modifier = Modifier.padding(start = if (subcategory != "NA") 8.dp else 0.dp),
                label = { Text(app.title) },
                selected = selection.value == app,
                onClick = { selection.value = app }
            )
        }
    }
}