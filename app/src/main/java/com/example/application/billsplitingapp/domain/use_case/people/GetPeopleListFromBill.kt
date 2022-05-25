package com.example.application.billsplitingapp.domain.use_case.people

import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class GetPeopleListFromBill @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(billId: Int): List<Person> {
        return repository.getPeopleFromBillList(billId)
    }
}