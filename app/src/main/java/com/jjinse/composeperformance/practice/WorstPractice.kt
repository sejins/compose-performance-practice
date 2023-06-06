package com.jjinse.composeperformance.practice

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
