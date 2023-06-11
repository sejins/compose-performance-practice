package com.jjinse.composeperformance.practice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jjinse.composeperformance.utils.log

fun heavyCalculation(value: String): String {
    log("Heavy calculation!")
    return "$value after heavy calculation"
}

@Composable
fun Item(
    text: String,
    logEnabled: Boolean = true
) {
    if (logEnabled) {
        log("Item($text) recomposition!")
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = text)
    }
}
