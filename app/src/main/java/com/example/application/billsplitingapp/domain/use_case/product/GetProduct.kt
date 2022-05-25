package com.example.application.billsplitingapp.domain.use_case.product

import androidx.room.Insert
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class GetProduct @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(id: Int): Product? {
        return repository.getProductById(id)
    }
}