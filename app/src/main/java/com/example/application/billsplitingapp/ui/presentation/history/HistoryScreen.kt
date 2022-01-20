package com.example.application.billsplitingapp.ui.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.ui.Screen
import com.example.application.billsplitingapp.ui.components.BackTitleHeader
import com.example.application.billsplitingapp.ui.components.DeleteButtonRow
import com.example.application.billsplitingapp.ui.components.HistoryCardItem
import com.example.application.billsplitingapp.ui.components.SwipeToDeleteBackground
import com.example.application.billsplitingapp.utils.addAllDistinct

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(navController: NavController, viewModel: HistoryViewModel = hiltViewModel()) {

    val selectedBillList: MutableList<Bill> = remember { mutableStateListOf() }

    val state = viewModel.state.value

    val selectionMode = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        Column(modifier = Modifier.background(color = MaterialTheme.colors.background)) {

            Box(modifier = Modifier.fillMaxWidth()) {

                if (selectionMode.value) {
                    DeleteButtonRow(
                        isAllSelected = selectedBillList.size >= state.bills.size,
                        onCheckedChange = { checked ->
                            if (checked) {
                                selectedBillList.addAllDistinct(state.bills)
                            } else {
                                selectedBillList.clear()
                                selectionMode.value = false
                            }
                        },
                        onDeleteClick = {
                            selectionMode.value = false
                            viewModel.deleteBills(selectedBillList)
                        }
                    )
                } else {
                    BackTitleHeader(
                        title = "Histórico",
                        navController = navController,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }

            if (state.bills.isEmpty() && !state.isLoading) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Nenhuma comanda ainda :(",
                    textAlign = TextAlign.Center
                )
            } else if (state.bills.isNotEmpty() && !state.isLoading) {
                LazyColumn() {
                    itemsIndexed(state.bills) { index, bill ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    viewModel.deleteBills(listOf(bill))
                                }
                                it != DismissValue.DismissedToStart
                            }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(
                                DismissDirection.EndToStart
                            ),
                            dismissThresholds = { direction ->
                                FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
                            },
                            background = {
                                SwipeToDeleteBackground(dismissState = dismissState)
                            },
                            dismissContent = {
                                HistoryCardItem(
                                    index = index,
                                    bill = bill,
                                    selectionMode = selectionMode.value,
                                    isSelected = selectedBillList.contains(bill),
                                    onClick = {
                                        navController.navigate(Screen.Bill.route + "/?billId=${bill.id}")
                                    },
                                    onLongPress = { selected, bill ->
//                                        navController.navigate(Screen.Bill.route + "/?billId=${bill.id}")
                                        if (!selectionMode.value) {
                                            selectionMode.value = true
                                        }
                                        if (selected) {
                                            selectedBillList.add(bill)
                                        } else {
                                            selectedBillList.remove(bill)
                                        }
                                        if (selectedBillList.isEmpty()) {
                                            selectionMode.value = false
                                        }
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }

}