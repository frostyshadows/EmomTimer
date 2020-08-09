package com.sherryyuan.emomtimer

import java.text.DecimalFormat

// Only show decimals if they're nonzero
fun Double.toFormattedString(): String = DecimalFormat("##.##").format(this)