package com.example.application.billsplitingapp.domain.use_case.bill

import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class UpdateBillValue @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(billId: Int, value: Float) {
        repository.updateBillValue(billId, value)
    }
}