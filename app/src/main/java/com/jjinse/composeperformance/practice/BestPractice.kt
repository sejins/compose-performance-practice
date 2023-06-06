package com.jjinse.composeperformance.practice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jjinse.composeperformance.utils.log
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

/**
 * @see WorstDerivedValue
 */
@Composable
fun BestDerivedValue() {
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
        ) {
            items(List(50) { it }) {
                Item(text = it.toString())
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // derivedStateOf -> state로 부터 파생된 값이 언제 recomposition을 트리거할 것 인지 (정확히) 결정
        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }


        if (showButton) {
            log("Button recomposition!") // recomposition once when listState.firstVisibleItemIndex > 0
            Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = { }) {
                Text(text = "BUTTON")
            }
        }
    }
}
