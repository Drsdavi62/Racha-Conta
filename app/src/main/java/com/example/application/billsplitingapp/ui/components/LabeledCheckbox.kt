package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun LabeledCheckbox(
    modifier: Modifier = Modifier,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.button,
    labelColor: Color = MaterialTheme.colors.onBackground,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = Modifier.padding(end = 4.dp),
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(it)
            },
            enabled = true,
        )
        Text(text = label, style = labelStyle, color = labelColor, modifier = Modifier.clickable {
            onCheckedChange(!isChecked)
        })
    }
}