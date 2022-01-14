package com.example.application.billsplitingapp.ui.presentation.products.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.example.application.billsplitingapp.ui.theme.MoneyGreen

@Composable
fun TotalValueAnimated(
    triggered: Boolean = false,
    value: String
) {
    val fontSize by animateFloatAsState(
        if (triggered) 18f else 14f,
    )
    Text(value, color = MoneyGreen, fontSize = fontSize.sp)
}