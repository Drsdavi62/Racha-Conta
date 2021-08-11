package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.models.BillModel
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.utils.Formatter

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun HistoryCardItem(
    index: Int,
    bill: BillModel,
    personList: List<List<PersonModel>>,
    selectionMode: Boolean,
    onClick: () -> Unit,
    onLongPress: (Boolean, Int) -> Unit
) {

    val isSelected = remember {
        mutableStateOf(false)
    }

    val longClick: (Int) -> Unit = { id ->
        isSelected.value = !isSelected.value
        onLongPress(isSelected.value, id)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .combinedClickable(
                onClick = {
                    if (selectionMode) {
                        longClick(bill.id)
                    } else {
                        onClick()
                    }
                },
                onLongClick = {
                    longClick(bill.id)
                }
            ),
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        backgroundColor = if (isSelected.value) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = bill.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = Formatter.currencyFormat(bill.value),
                    style = MaterialTheme.typography.h6
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconText(
                    icon = Icons.Filled.People,
                    text = personList[index].joinToString(", ") { it.name },
                    iconTint = if (isSelected.value) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant
                )
                IconText(
                    icon = Icons.Filled.DateRange,
                    text = bill.date,
                    iconTint = if (isSelected.value) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}