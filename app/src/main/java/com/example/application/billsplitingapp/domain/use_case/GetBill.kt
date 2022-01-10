package com.example.application.billsplitingapp.domain.use_case

import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class GetBill @Inject constructor(val repository: BillRepository) {

    suspend operator fun invoke(id: Int): Bill? {
        return repository.getBillById(id)
    }
}