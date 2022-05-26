package com.example.application.billsplitingapp.ui.presentation.products.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.ui.components.IconButtonText
import com.example.application.billsplitingapp.ui.components.IconText
import com.example.application.billsplitingapp.ui.theme.moneyGreen
import com.example.application.billsplitingapp.utils.Formatter
import com.example.application.billsplitingapp.utils.createTicketClipPath

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    product: Product,
    isFirst: Boolean,
    isLast: Boolean,
    onPlusAmountClick: (Int, Int) -> Unit,
    onMinusAmountClick: (Int, Int) -> Unit,
    onEditClick: () -> Unit,
    selectionMode: Boolean,
    isSelected: Boolean,
    onLongPress: (Boolean, Product) -> Unit
) {

    val tickAmount = 34

    val isExpanded = remember { mutableStateOf(false) }

    val fullValue by lazy { product.value * product.amount }

    val longClick: (Product) -> Unit = { bill ->
        onLongPress(!isSelected, bill)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
        val alpha = if (isSelected) 1.0f else 0.3f

        Canvas(modifier = Modifier
            .matchParentSize()
            .clickable {
                isExpanded.value = !isExpanded.value
            }) {

            val tickHeight = 10.dp.toPx()

            val clipPath = Path().createTicketClipPath(
                width = size.width,
                height = size.height,
                tickHeight = tickHeight,
                tickAmount = tickAmount,
                isFirst = isFirst,
                isLast = isLast
            )

            clipPath(clipPath) {
                drawRoundRect(
                    color = color,
                    alpha = alpha,
                    size = size,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 8.dp)
                .combinedClickable(
                    onClick = {
                        if (selectionMode) {
                            longClick(product)
                        }
                    },
                    onLongClick = {
                        longClick(product)
                    }
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    AmountChanger(
                        amount = product.amount,
                        onPlusClick = { amount ->
                            onPlusAmountClick(product.id, amount)
                        },
                        onMinusClick = { amount ->
                            onMinusAmountClick(product.id, amount)
                        }
                    )
                    Column {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        AnimatedVisibility(
                            visible = isExpanded.value,
                            enter = expandVertically(expandFrom = Alignment.Top),
                            exit = shrinkVertically(shrinkTowards = Alignment.Top),
                        ) {
                            Row() {
                                val peopleDivided = product.people.map { it.name }.chunked(3)
                                peopleDivided.forEach { names ->
                                    Text(
                                        text = "- " + names.joinToString("\n- "),
                                        style = MaterialTheme.typography.body1,
                                        maxLines = 3
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    visible = !isExpanded.value,
                    enter = expandHorizontally(),
                    exit = shrinkHorizontally(),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                ) {
                    Column(
                        Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = "")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = Formatter.currencyFormatFromFloat(product.value * product.amount),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.moneyGreen
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        IconText(
                            icon = Icons.Default.Group,
                            text = product.people.size.toString(),
                            textColor = MaterialTheme.colors.primaryVariant,
                        )
                    }

                }
            }

            AnimatedVisibility(
                visible = isExpanded.value,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        color = MaterialTheme.colors.primaryVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
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
                        Column {
                            Text(
                                text = Formatter.currencyFormatFromFloat(fullValue),
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.moneyGreen
                            )
                            Text(
                                text = Formatter.currencyFormatFromFloat(product.value) + " cada",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.moneyGreen
                            )
                        }
                    }
                }
            }
        }

    }
}