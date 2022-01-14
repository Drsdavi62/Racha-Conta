package com.example.application.billsplitingapp.ui.presentation.products.list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.ui.presentation.bill.BottomNavigationBar
import com.example.application.billsplitingapp.ui.presentation.products.components.ProductItem
import com.example.application.billsplitingapp.ui.presentation.products.list.ProductListEvents
import com.example.application.billsplitingapp.ui.presentation.products.list.ProductListViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel(),
    billId: Int
) {

    viewModel.onEvent(ProductListEvents.LoadProducts(billId))

    val products = viewModel.products.value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(products) { i, product ->
                ProductItem(
                    product = product,
                    isFirst = i == 0,
                    isLast = i == products.size - 1,
                    onPlusAmountClick = { productId, amount ->
                        viewModel.onEvent(ProductListEvents.ChangeAmount(productId, amount))
                    },
                    onMinusAmountClick = { productId, amount ->
                        viewModel.onEvent(ProductListEvents.ChangeAmount(productId, amount))
                    },
                    onEditClick = {

                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }


}