package com.example.application.billsplitingapp.domain.repository

import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    fun getBills(): Flow<List<Bill>>

    suspend fun getBillById(id: Int): Resource<Bill>

    suspend fun insertBill(bill: Bill): Int

    suspend fun deleteBill(bill: Bill)
}