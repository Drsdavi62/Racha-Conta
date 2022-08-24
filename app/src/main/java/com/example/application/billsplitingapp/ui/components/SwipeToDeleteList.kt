package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.ui.presentation.products.list.ProductListEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDeleteList(
    list: List<T>,
    key: ((Int, T) -> Any),
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    itemNameOnSnackbar: (T) -> String,
    onUndoDeletion: () -> Unit,
    onDeleteBySwipe: (T) -> Unit,
    itemContent: @Composable (index: Int, item: T) -> Unit
) {

    val snackbarScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(
            items = list,
            key = key
        ) { index: Int, item: T ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        onDeleteBySwipe(item)
                        snackbarScope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "${itemNameOnSnackbar(item)} excluído",
                                actionLabel = "Desfazer"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onUndoDeletion()
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
                    itemContent(index, item)
                }
            )
        }
    }
}