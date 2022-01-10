package com.example.application.billsplitingapp.domain.use_case

import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class DeleteBill @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(bill: Bill) {
        repository.deleteBill(bill)
    }
}