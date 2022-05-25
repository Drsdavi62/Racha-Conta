package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HomeButton(
    icon: ImageVector,
    text: String,
    width: Dp,
    outline: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(width - 40.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        colors =
        if (outline)
            ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
        else
            ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(50.dp),
            )
            Text(text = text, textAlign = TextAlign.Center)
        }
    }
}