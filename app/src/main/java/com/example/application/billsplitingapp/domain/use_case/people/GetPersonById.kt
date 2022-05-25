package com.example.application.billsplitingapp.domain.use_case.people

import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.repository.BillRepository
import javax.inject.Inject

class GetPersonById @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(id: Int): Person? {
        return repository.getFullPersonById(id)
    }
}