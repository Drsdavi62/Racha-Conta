package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun IconText(
    icon: ImageVector,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body1
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = "icon", Modifier.padding(end = 4.dp), tint = MaterialTheme.colors.primaryVariant)
        Text(text = text, style = textStyle)
    }
}