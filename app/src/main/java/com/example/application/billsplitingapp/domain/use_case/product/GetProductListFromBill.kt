package com.example.application.billsplitingapp.domain.use_case.product

import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class GetProductListFromBill @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(billId: Int): List<Product> {
        return repository.getProductListFromBill(billId)
    }
}