package com.example.application.billsplitingapp.domain.use_case.people

import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class InsertPerson @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(person: Person) {
        repository.insertPerson(person)
    }
}