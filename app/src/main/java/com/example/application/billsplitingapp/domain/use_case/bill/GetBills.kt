package com.example.application.billsplitingapp.domain.use_case.bill

import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.repository.BillRepository
import com.example.application.billsplitingapp.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBills @Inject constructor(
    private val repository: BillRepository
) {

    operator fun invoke(): Flow<List<Bill>> {
        return repository.getBills()
    }
}