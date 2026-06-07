package com.faangx.ktp.catalog

fun MiniApp.Companion.groupedByCategory(): Map<String, List<MiniApp>> {
    return MiniApp.entries.groupBy { it.category }
}

fun List<MiniApp>.groupedBySubcategory(): Map<String, List<MiniApp>> {
    return groupBy { it.subcategory ?: "NA" }
}