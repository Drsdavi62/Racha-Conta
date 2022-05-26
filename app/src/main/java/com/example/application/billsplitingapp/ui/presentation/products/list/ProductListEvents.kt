package com.example.application.billsplitingapp.ui.presentation.products.list

import com.example.application.billsplitingapp.domain.model.Product

sealed class ProductListEvents {
    data class LoadProducts(val billId: Int): ProductListEvents()
    data class DeleteProduct(val product: Product): ProductListEvents()
    data class DeleteMultipleProducts(val products: List<Product>): ProductListEvents()
    data class ChangeAmount(val id: Int, val amount: Int): ProductListEvents()
}
