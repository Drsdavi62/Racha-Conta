package com.example.application.billsplitingapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 4.dp,
    color: Color = MaterialTheme.colors.primary,
    listTotalHeight: Dp
): Modifier {

    var maxVisibleItems: Int = 0
    if (state.layoutInfo.visibleItemsInfo.isNotEmpty()) {
        maxVisibleItems = remember {
            state.layoutInfo.visibleItemsInfo.size
        }
    }

    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    val elementHeight = listTotalHeight / state.layoutInfo.totalItemsCount
    val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

    var heightInPx: Float

    with(LocalDensity.current) {
        heightInPx = elementHeight.toPx()
    }
    val scrollbarOffsetY = firstVisibleElementIndex?.times(heightInPx) ?: 0f

    val offset by animateFloatAsState(
        targetValue = scrollbarOffsetY,
        animationSpec = tween(duration)
    )
    val scrollbarHeight = maxVisibleItems * heightInPx

    return drawWithContent {
        drawContent()
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {

            drawRect(
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), offset),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}