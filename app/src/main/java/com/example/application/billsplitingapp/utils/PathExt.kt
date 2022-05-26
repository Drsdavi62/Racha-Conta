package com.example.application.billsplitingapp.utils

import androidx.compose.ui.graphics.Path


fun Path.createTicketClipPath(width: Float, height: Float, tickHeight: Float, tickAmount: Int, isFirst: Boolean, isLast: Boolean): Path {
    return apply {
        lineTo(0f, height)
        if (isLast && !isFirst) {
            lineTo(width, height)
        } else {
            for (i in 1..tickAmount) {
                if (i % 2 != 0) {
                    lineTo(i * (width / tickAmount), height - tickHeight)
                } else {
                    lineTo(i * (width / tickAmount), height)
                }
            }
        }
        if (isFirst && !isLast) {
            lineTo(width, 0f)
            close()
        } else {
            lineTo(width, tickHeight)
            for (i in tickAmount - 1 downTo 1) {
                if (i % 2 != 0) {
                    lineTo(i * (width / tickAmount), 0f)
                } else {
                    lineTo(i * (width / tickAmount), tickHeight)
                }
            }
            lineTo(0f, tickHeight)
        }
    }
}