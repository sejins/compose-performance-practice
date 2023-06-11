package com.jjinse.composeperformance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jjinse.composeperformance.practice.BestDerivedValue
import com.jjinse.composeperformance.practice.BestReadingState
import com.jjinse.composeperformance.practice.WorstReadingState
import com.jjinse.composeperformance.ui.theme.ComposePerformanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePerformanceTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BestReadingState()
                }
            }
        }
    }
}
