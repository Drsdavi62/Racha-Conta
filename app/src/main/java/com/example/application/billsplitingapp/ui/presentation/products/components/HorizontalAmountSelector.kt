package com.example.application.billsplitingapp.ui.presentation.products.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalAmountSelector(
    amount: Int,
    onAmountChange: (Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { onAmountChange(amount - 1) },
            modifier = Modifier.wrapContentSize(),
            enabled = amount > 1
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Minus",
                modifier = Modifier
                    .size(40.dp),
                tint = MaterialTheme.colors.secondary
            )
        }
        Text(
            text = amount.toString(),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = {
                onAmountChange(amount + 1)
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Plus",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colors.secondary
            )
        }
    }
}