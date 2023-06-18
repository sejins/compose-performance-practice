package com.jjinse.composeperformance.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jjinse.composeperformance.R
import com.jjinse.composeperformance.utils.log

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

/**
 * 특정 state로 부터 파생되는 값을 사용할 때는 불필요한 recomposition이 발생할 가능성이 있음
 * @see WorstDerivedValue
 */
@Composable
fun WorstDerivedValue() {
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

        // Warning : Frequently changing state should not be directly read in composable function
        // 스크롤에 따른 listState로 파생되는 showButton은 다수의 recompostion을 발생시킴
        val showButton = listState.firstVisibleItemIndex > 0

        /* remember 를 사용하는 꼼수도 위와 동일하게 동작함
        val showButton2 = remember(listState.firstVisibleItemIndex) {
            listState.firstVisibleItemIndex > 0
        }
         */

        if (showButton) {
            log("Button recomposition!") // multiple recomposition
            Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = { }) {
                Text(text = "BUTTON")
            }
        }
    }
}

@Composable
fun WorstReadingState() {
    log("WorstReadingState Recompostion")

    Box(modifier = Modifier.fillMaxWidth()) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            (0..60).forEach {
                Item(text = it.toString(), logEnabled = false)
            }
        }

        // Recomposition Scope : WorstReadingState block
        WorstTitle(title = "This is a title", scroll = scrollState.value)
    }
}

@Composable
private fun WorstTitle(
    modifier: Modifier = Modifier,
    title: String,
    scroll: Int
) {
    log("Title recomposition")
    val offset = with(LocalDensity.current) { scroll.toDp() }

    Text(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = offset),
        text = title,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MultipleFrameRequired() {
    log("MultipleFrameRequired Composable")
    Box {
        var imageHeightPx by remember { mutableStateOf(0) }

        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "I'm above the text",
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { size ->
                    // Don't do this
                    imageHeightPx = size.height
                }
        )

        Text(
            text = "I'm below the image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                top = with(LocalDensity.current) { imageHeightPx.toDp() }
            ),
            textAlign = TextAlign.Center
        )
    }
}
