package com.jjinse.composeperformance.practice

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

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
