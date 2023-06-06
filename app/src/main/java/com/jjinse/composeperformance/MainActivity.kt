package com.jjinse.composeperformance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jjinse.composeperformance.practice.BestCalculation
import com.jjinse.composeperformance.practice.BestLazyLayout
import com.jjinse.composeperformance.practice.WorstCalculation
import com.jjinse.composeperformance.practice.WorstLazyLayout
import com.jjinse.composeperformance.ui.theme.ComposePerformanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePerformanceTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        val list: SnapshotStateList<Long> = remember {
                            mutableStateListOf(1, 2, 3, 4, 5)
                        }
                        BestLazyLayout(list = list)
                        Button(onClick = {
                            list.add(index = 2, element = System.currentTimeMillis())
                        }) {
                            Text(text = "append value")
                        }
                    }
                }
            }
        }
    }
}
