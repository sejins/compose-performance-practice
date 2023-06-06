package com.jjinse.composeperformance.practice

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * value2 변경에 의한 recomposition에도 heavyCalculation이 매번 동작
 * @see BestCalculation
 */
@Composable
fun WorstCalculation(
    value1: String,
    value2: Int
) {
    val newValue1 = heavyCalculation(value1)
    Text(text = "$value2 $newValue1")
}


/**
 * 리스트 아이템 추가 및 순서 변경의 경우 전체 아이템이 recomposition
 * @see BestLazyLayout
 */
@Composable
fun WorstLazyLayout(list: List<Long>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 4.dp)) {
        items(list) {
            Item(text = it.toString())
        }
    }
}
