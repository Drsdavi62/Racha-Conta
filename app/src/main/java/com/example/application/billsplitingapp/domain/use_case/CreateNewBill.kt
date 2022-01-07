package com.example.application.billsplitingapp.domain.use_case

import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.repository.BillRepository
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class CreateNewBill @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(name: String): Int {
        val bill = Bill(
            name = name,
            value = 0f,
            date = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time),
            products = emptyList(),
            people = emptyList()
        )
        return repository.insertBill(bill)
    }
}