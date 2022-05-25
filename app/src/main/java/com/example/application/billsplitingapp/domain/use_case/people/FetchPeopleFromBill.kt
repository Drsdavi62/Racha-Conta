package com.example.application.billsplitingapp.domain.use_case.people

import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPeopleFromBill @Inject constructor(
    private val repository: BillRepository
) {

    operator fun invoke(billId: Int): Flow<List<Person>> = repository.getFullPeopleFromBill(billId)
}