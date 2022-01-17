package com.example.application.billsplitingapp.ui.presentation.products.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.example.application.billsplitingapp.ui.theme.MoneyGreen
import com.example.application.billsplitingapp.utils.Formatter

@Composable
fun TotalValueAnimated(
    triggered: Boolean = false,
    value: String
) {
    val fontSize by animateFloatAsState(
        if (triggered) 18f else 14f,
    )
    Text("Total: $value", color = MoneyGreen, fontSize = fontSize.sp)
}

@Composable
fun TotalValueAnimated(
    triggered: Boolean = false,
    value: Float
) {
    TotalValueAnimated(value = Formatter.currencyFormatFromFloat(value), triggered = triggered)
}