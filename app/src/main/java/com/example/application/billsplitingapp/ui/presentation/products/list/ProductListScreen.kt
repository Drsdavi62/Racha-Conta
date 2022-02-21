package com.example.application.billsplitingapp.ui.presentation.products.list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.ui.components.SwipeToDeleteBackground
import com.example.application.billsplitingapp.ui.presentation.products.ProductScreens
import com.example.application.billsplitingapp.ui.presentation.products.components.ProductItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductListScreen(
    navController: NavController,
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
            itemsIndexed(
                products,
                key = { _, product: Product -> product.id }
            ) { i, product ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.onEvent(ProductListEvents.DeleteProduct(product))
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
                                navController.navigate(ProductScreens.AddEditProductScreen.route + "/?billId=${billId}&productId=${product.id}")
                            },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                )
            }

        }
    }


}