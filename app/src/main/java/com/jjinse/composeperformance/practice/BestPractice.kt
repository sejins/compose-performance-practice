package com.jjinse.composeperformance.practice

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import java.util.UUID

/**
 * @see WorstCalculation
 */
@Composable
fun BestCalculation(
    value1: String,
    value2: Int
) {
    val newValue1 = remember(value1) {
        heavyCalculation(value1)
    }
    Text(text = "$value2 $newValue1")
}

/**
 * @see WorstLazyLayout
 */
@Composable
fun BestLazyLayout(list: List<Long>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 4.dp)) {
        items(
            items = list,
            key = { it }
        ) {
            Item(text = it.toString())
        }
    }
}
