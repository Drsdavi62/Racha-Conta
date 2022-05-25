package com.example.application.billsplitingapp.ui.presentation.people.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.ui.components.IconButtonText
import com.example.application.billsplitingapp.ui.components.IconText
import com.example.application.billsplitingapp.ui.theme.moneyGreen
import com.example.application.billsplitingapp.utils.Formatter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PersonItem(
    person: Person,
    onEditClick: () -> Unit
) {

    val isExpanded = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                isExpanded.value = !isExpanded.value
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
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
                AnimatedVisibility(
                    visible = !isExpanded.value,
                    enter = expandHorizontally(),
                    exit = shrinkHorizontally(),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                ) {
                    Text(
                        text = Formatter.currencyFormatFromFloat(person.value),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.moneyGreen
                    )

                }
                if (!isExpanded.value) {
                    Spacer(modifier = Modifier.height(16.dp))
                    IconText(
                        icon = Icons.Default.LunchDining,
                        text = person.products.size.toString(),
                        textColor = MaterialTheme.colors.primaryVariant,
                    )
                }
            }

        }

        AnimatedVisibility(
            visible = isExpanded.value,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                person.products.forEach { product ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "• " + product.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(
                            text = Formatter.currencyFormatFromFloat(product.value),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colors.moneyGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButtonText(
                        onClick = onEditClick,
                        icon = Icons.Outlined.Edit,
                        text = "Editar",
                        textColor = MaterialTheme.colors.primaryVariant,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = Formatter.currencyFormatFromFloat(person.value),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.moneyGreen,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

        }
    }


}