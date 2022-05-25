package com.example.application.billsplitingapp.ui.presentation.products.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.ui.presentation.products.add_edit.AddEditProductEvents

@Composable
fun CheckboxItem(
    checked: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    onCheckedChange: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            },
            colors = CheckboxDefaults.colors()
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 10.dp)
        )
    }
}