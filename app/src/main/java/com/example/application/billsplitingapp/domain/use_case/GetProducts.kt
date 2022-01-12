package com.example.application.billsplitingapp.domain.use_case

import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val repository: BillRepository
) {

    operator fun invoke(billId: Int): Flow<List<Product>> {
        return repository.getProductsFromBill(billId)
    }
}