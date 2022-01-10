package com.example.application.billsplitingapp.data.repository


import com.example.application.billsplitingapp.data.cache.BillDao
import com.example.application.billsplitingapp.data.cache.model.toBill
import com.example.application.billsplitingapp.data.cache.model.toBillEntity
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.repository.BillRepository
import com.example.application.billsplitingapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val dao: BillDao
) : BillRepository {

    override fun getBills(): Flow<List<Bill>> {
        return dao.getBills().map { billEntities -> billEntities.map { it.toBill() } }
    }

    override suspend fun getBillById(id: Int): Resource<Bill> {
        return try {
            val bill = dao.getBillById(id).toBill()
            Resource.Success<Bill>(data = bill)
        } catch (e: Exception) {
            Resource.Error<Bill>(message = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun insertBill(bill: Bill): Int {
            val insertedId = dao.insertBill(bill.toBillEntity())
            return insertedId.toInt()
    }

    override suspend fun deleteBill(bill: Bill) {
        dao.deleteBill(bill.toBillEntity())
    }
}