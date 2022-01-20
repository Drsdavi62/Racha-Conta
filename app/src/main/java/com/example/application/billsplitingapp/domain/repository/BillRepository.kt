package com.example.application.billsplitingapp.domain.repository

import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    fun getBills(): Flow<List<Bill>>

    suspend fun getBillById(id: Int): Bill?

    suspend fun insertBill(bill: Bill): Int

    suspend fun deleteBill(bill: Bill)

    suspend fun insertProduct(product: Product)

    fun getProductsFromBill(billId: Int): Flow<List<Product>>

    suspend fun updateBillValue(billId: Int, value: Float)

    suspend fun updateProductAmount(productId: Int, amount: Int)

    suspend fun getProductById(id: Int): Product?

    suspend fun deleteProduct(product: Product)
}