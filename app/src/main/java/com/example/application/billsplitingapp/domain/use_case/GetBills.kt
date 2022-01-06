package com.example.application.billsplitingapp.domain.use_case

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

    operator fun invoke(): Flow<Resource<List<Bill>>> = flow {
        emit(Resource.Loading<List<Bill>>())
        delay(3000)
        emit(repository.getBills())
    }
}