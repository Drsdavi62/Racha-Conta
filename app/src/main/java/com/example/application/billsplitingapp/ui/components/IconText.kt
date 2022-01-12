package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun IconText(
    icon: ImageVector,
    text: String,
    iconTint: Color = MaterialTheme.colors.primaryVariant,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = Color.Unspecified,
    fontWeight: FontWeight? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon, contentDescription = "icon", Modifier.padding(end = 4.dp),
            tint = iconTint
        )
        Text(text = text, style = textStyle, color = textColor, fontWeight = fontWeight)
    }
}

@Composable
fun IconButtonText(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    iconTint: Color = MaterialTheme.colors.primaryVariant,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = Color.Unspecified,
    fontWeight: FontWeight? = null
) {
    TextButton(onClick = onClick) {
        IconText(
            icon = icon,
            text = text,
            iconTint = iconTint,
            textStyle = textStyle,
            textColor = textColor,
            fontWeight = fontWeight
        )
    }
}