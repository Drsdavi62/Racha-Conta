package com.example.application.billsplitingapp.domain.repository

import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.utils.Resource

interface BillRepository {

    suspend fun getBills(): Resource<List<Bill>>

    suspend fun getBillById(id: Int): Resource<Bill>

    suspend fun insertBill(bill: Bill): Resource<Bill>

    suspend fun deleteBill(bill: Bill)
}