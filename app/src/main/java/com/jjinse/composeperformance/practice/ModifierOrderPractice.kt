package com.jjinse.composeperformance.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jjinse.composeperformance.R

/**
 * Constaint & Modifier order
 * https://medium.com/androiddevelopers/constraints-and-modifier-order-a3912461ecd6
 */

@Composable
fun ModifierOrderPractice() {
    val modifierTest1 = Modifier
        .fillMaxSize()
        .size(50.dp)

    val modifierTest2 = Modifier
        .fillMaxSize()
        .wrapContentSize() // minimum constraint reset
        .background(color = Color.Blue)
        .size(50.dp)

    val modifierTest3 = Modifier
        .clip(CircleShape)
        .padding(10.dp)
        .size(100.dp)

    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "",
        modifier = modifierTest3,
    )
}
