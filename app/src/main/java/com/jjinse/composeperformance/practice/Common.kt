package com.jjinse.composeperformance.practice

import com.jjinse.composeperformance.utils.log

internal fun heavyCalculation(value: String): String {
    log("Heavy calculation!")
    return "$value after heavy calculation"
}
