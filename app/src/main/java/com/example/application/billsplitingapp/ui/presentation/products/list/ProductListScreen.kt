package com.example.application.billsplitingapp.ui.presentation.products.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.ui.components.SwipeToDeleteList
import com.example.application.billsplitingapp.ui.presentation.products.ProductScreens
import com.example.application.billsplitingapp.ui.presentation.products.components.ProductItem
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductListScreen(
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
    billId: Int
) {

    LaunchedEffect(key1 = billId) {
        viewModel.onEvent(ProductListEvents.LoadProducts(billId))
    }

    val products = viewModel.products.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        scaffoldState = scaffoldState
    ) {
        Column(Modifier.fillMaxSize()) {
            SwipeToDeleteList(
                list = products,
                key = { _, product: Product -> product.id },
                scaffoldState = scaffoldState,
                onDeleteBySwipe = { product ->
                    viewModel.onEvent(ProductListEvents.DeleteProduct(product))
                },
                itemNameOnSnackbar = { product -> product.name },
                onUndoDeletion = { viewModel.onEvent(ProductListEvents.UndoDelete) },
            ) { index: Int, product: Product ->
                ProductItem(
                    product = product,
                    isFirst = index == 0,
                    isLast = index == products.size - 1,
                    onPlusAmountClick = { productId, amount ->
                        viewModel.onEvent(ProductListEvents.ChangeAmount(productId, amount))
                    },
                    onMinusAmountClick = { productId, amount ->
                        viewModel.onEvent(ProductListEvents.ChangeAmount(productId, amount))
                    },
                    onEditClick = {
                        navController.navigate(ProductScreens.AddEditProductScreen.route + "/?billId=${billId}&productId=${product.id}")
                    },
                    onDeleteClick = {
                        viewModel.onEvent(ProductListEvents.DeleteProduct(product))
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }


}