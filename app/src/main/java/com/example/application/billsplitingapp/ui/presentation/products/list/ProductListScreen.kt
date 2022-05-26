package com.example.application.billsplitingapp.ui.presentation.products.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.ui.Screen
import com.example.application.billsplitingapp.ui.components.DeleteButtonRow
import com.example.application.billsplitingapp.ui.components.SwipeToDeleteBackground
import com.example.application.billsplitingapp.ui.presentation.products.ProductScreens
import com.example.application.billsplitingapp.ui.presentation.products.components.ProductItem
import com.example.application.billsplitingapp.utils.addAllDistinct
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductListScreen(
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
    billId: Int
) {

    viewModel.onEvent(ProductListEvents.LoadProducts(billId))

    val products = viewModel.products.value

    val selectedProductList: MutableList<Product> = remember { mutableStateListOf() }

    val selectionMode = remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when(it) {
                ProductListViewModel.UIEvents.DeletedProduct -> {

                }
            }
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        scaffoldState = scaffoldState
    ) {
        Column(Modifier.fillMaxSize()) {
            if (selectionMode.value) {
                DeleteButtonRow(
                    isAllSelected = selectedProductList.size >= products.size,
                    onCheckedChange = { checked ->
                        if (checked) {
                            selectedProductList.addAllDistinct(products)
                        } else {
                            selectedProductList.clear()
                            selectionMode.value = false
                        }
                    },
                    onDeleteClick = {
                        selectionMode.value = false
                        viewModel.onEvent(ProductListEvents.DeleteMultipleProducts(selectedProductList))
                    }
                )
            }

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
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "${product.name} excluído",
                                        actionLabel = "Desfazer"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(ProductListEvents.UndoDelete)
                                    }
                                }
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
                                selectionMode = selectionMode.value,
                                isSelected = selectedProductList.contains(product),
                                onLongPress = { selected, product ->
                                    if (!selectionMode.value) {
                                        selectionMode.value = true
                                    }
                                    if (selected) {
                                        selectedProductList.add(product)
                                    } else {
                                        selectedProductList.remove(product)
                                    }
                                    if (selectedProductList.isEmpty()) {
                                        selectionMode.value = false
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    )
                }

            }
        }
    }


}