package com.example.application.billsplitingapp.ui.presentation.products.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.use_case.bill.UpdateBillValue
import com.example.application.billsplitingapp.domain.use_case.product.DeleteProduct
import com.example.application.billsplitingapp.domain.use_case.product.GetProducts
import com.example.application.billsplitingapp.domain.use_case.product.UpdateProductAmount
import com.example.application.billsplitingapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProducts: GetProducts,
    private val updateProductAmount: UpdateProductAmount,
    private val updateBillValue: UpdateBillValue,
    private val deleteProduct: DeleteProduct
) : ViewModel() {

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    var job: Job? = null

    private var billId: Int = -1

    fun onEvent(event: ProductListEvents) {
        when (event) {
            is ProductListEvents.LoadProducts -> {
                loadProducts(event.billId)
            }
            is ProductListEvents.DeleteProduct -> {
                viewModelScope.launch {
                    deleteProduct(event.product)
                }
            }
            is ProductListEvents.ChangeAmount -> {
                viewModelScope.launch {
                    updateProductAmount(event.id, event.amount)
                    updateBillValue(billId,
                        products.value.map { if (it.id != event.id) it.fullValue else it.value * event.amount }
                            .sum()
                    )
                }
            }
        }
    }

    private fun loadProducts(billId: Int) {
        this.billId = billId
        job?.cancel()
        job = getProducts(billId).onEach { products ->
            _products.value = products.reversed()
            updateBillValue(billId,
                products.map { it.fullValue }.sum()
            )
        }.launchIn(viewModelScope)
    }
}