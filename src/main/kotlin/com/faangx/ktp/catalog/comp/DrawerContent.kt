package com.faangx.ktp.catalog.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.faangx.ktp.catalog.MiniApp
import com.faangx.ktp.catalog.groupedByCategory
import com.faangx.ktp.catalog.groupedBySubcategory

@Composable
fun MiniAppsMenu(
    selection: MutableState<MiniApp>
) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

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
                .padding(12.dp),
        ) {
            Text(
                category,
                style = MaterialTheme.typography.titleMedium
            )

            val subcategories = remember { apps.groupedBySubcategory() }

            subcategories.forEach { (subcategory, apps) ->
                SubcategoryCard(
                    subcategory, apps, selection
                )
            }
        }
    }
}

@Composable
private fun SubcategoryCard(
    subcategory: String,
    apps: List<MiniApp>,
    selection: MutableState<MiniApp>
) {
    Column(
        modifier = Modifier.padding(
            start = if (subcategory != "NA") 8.dp else 0.dp,
            top = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (subcategory != "NA") {
            Text(
                subcategory,
                style = MaterialTheme.typography.titleSmall
            )
        }

        apps.forEach { app ->
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = if (subcategory != "NA") 8.dp else 0.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (selection.value == app) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    )
                    .clickable { selection.value = app }
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = app.title
            )
        }
    }
}