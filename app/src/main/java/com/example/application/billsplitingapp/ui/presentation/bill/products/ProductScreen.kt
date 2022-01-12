package com.example.application.billsplitingapp.ui.presentation.bill.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.application.billsplitingapp.ui.presentation.bill.BillViewModel
import com.example.application.billsplitingapp.ui.presentation.bill.products.components.ProductItem

@Composable
fun ProductScreen(viewModel: BillViewModel = hiltViewModel()) {

    val bill = viewModel.bill.value

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        bill?.let { bill ->
            itemsIndexed(bill.products) { i, product ->
                ProductItem(
                    product = product,
                    isFirst = i == 0,
                    isLast = i == bill.products.size - 1,
                    onPlusAmountClick = {

                    },
                    onMinusAmountClick = {

                    },
                    onEditClick = {

                    },
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}