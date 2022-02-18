package com.example.application.billsplitingapp.ui.presentation.people.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.ui.components.IconText
import com.example.application.billsplitingapp.ui.theme.MoneyGreen
import com.example.application.billsplitingapp.utils.Formatter

@Composable
fun PersonItem(
    person: Person
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = person.name,
            style = MaterialTheme.typography.h6,
            maxLines = 1
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = Formatter.currencyFormatFromFloat(person.value),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = MoneyGreen
            )
            Spacer(modifier = Modifier.height(16.dp))
            IconText(
                icon = Icons.Default.LunchDining,
                text = person.products.size.toString(),
                textColor = MaterialTheme.colors.primaryVariant,
            )
        }
    }

}