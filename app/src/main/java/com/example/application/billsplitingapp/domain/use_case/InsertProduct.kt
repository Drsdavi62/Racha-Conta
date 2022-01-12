package com.example.application.billsplitingapp.domain.use_case

import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class InsertProduct @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(product: Product) {
        repository.insertProduct(product)
    }
}