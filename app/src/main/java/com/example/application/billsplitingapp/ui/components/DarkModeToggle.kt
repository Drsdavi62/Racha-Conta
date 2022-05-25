package com.example.application.billsplitingapp.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DarkModeToggle(
    darkTheme: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            if (darkTheme)
                Icons.Filled.LightMode
            else
                Icons.Filled.DarkMode,
            contentDescription = "LightMode",
            tint = MaterialTheme.colors.secondary,
        )
    }
}