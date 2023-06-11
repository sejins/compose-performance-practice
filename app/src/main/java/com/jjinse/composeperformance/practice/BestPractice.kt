package com.jjinse.composeperformance.practice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
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

@Composable
fun BestReadingState() {
    log("BestReadingState Recompostion")

    Box(modifier = Modifier.fillMaxWidth()) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            (0..60).forEach {
                Item(text = it.toString(), logEnabled = false)
            }
        }

        // Recomposition block : BestTitle
        BestTitle1(title = "This is a title") { scrollState.value }
    }
}

@Composable
private fun BestTitle1(
    modifier: Modifier = Modifier,
    title: String,
    scrollProvider: () -> Int
) {
    log("Title recomposition")
    val offset = with(LocalDensity.current) { scrollProvider().toDp() }

    Text(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = offset),
        text = title,
        textAlign = TextAlign.Center
    )
}

// 리컴포지션이 발생하는 경우에도 건너뛸 수 있는 phase(composition, layout, draw)가 있는 지 고려해야함
// 아래처럼 scroll의 경우 layout phase 부터 수행할 수 있음
// color 변경의 경우에도 사실상 draw phase 만 수행하면 됨
// lamda 버전의 modifier는 위를 가능하게 함; 가능하다면 lambda 버전의 modifier를 사용하는 것이 좋음
@Composable
private fun BestTitle2(
    modifier: Modifier = Modifier,
    title: String,
    scrollProvider: () -> Int
) {
    log("Title recomposition")

    Text(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(x = 0, y = scrollProvider()) }, // 스크롤 시 lambda 버전의 offset modifier를 사용하면 layout phase 부터 수행이 가능함.
        text = title,
        textAlign = TextAlign.Center
    )
}
