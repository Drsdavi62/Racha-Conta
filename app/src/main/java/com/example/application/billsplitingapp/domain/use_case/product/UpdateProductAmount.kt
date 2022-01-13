package com.example.application.billsplitingapp.domain.use_case.product

import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class UpdateProductAmount @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(id: Int, amount: Int) {
        repository.updateProductAmount(id, amount)
    }
}