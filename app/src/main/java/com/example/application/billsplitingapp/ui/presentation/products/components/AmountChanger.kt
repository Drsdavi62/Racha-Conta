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
fun AmountChanger(
    amount: Int,
    onPlusClick: (Int) -> Unit,
    onMinusClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                onPlusClick(amount + 1)
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Plus",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Text(
            text = amount.toString(),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = { onMinusClick(amount - 1) },
            modifier = Modifier.wrapContentSize(),
            enabled = amount > 1,

        ) {
            if (amount > 1) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Minus",
                    modifier = Modifier
                        .size(25.dp),
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}