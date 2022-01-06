package com.example.application.billsplitingapp.data.repository


import com.example.application.billsplitingapp.data.cache.BillDao
import com.example.application.billsplitingapp.data.cache.model.toBill
import com.example.application.billsplitingapp.data.cache.model.toBillEntity
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.repository.BillRepository
import com.example.application.billsplitingapp.utils.Resource
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val dao: BillDao
) : BillRepository {

    override suspend fun getBills(): Resource<List<Bill>> {
        return try {
            val bills = dao.getBills().map { it.toBill() }
            Resource.Success<List<Bill>>(data = bills)
        } catch (e: Exception) {
            Resource.Error<List<Bill>>(message = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun getBillById(id: Int): Resource<Bill> {
        return try {
            val bill = dao.getBillById(id).toBill()
            Resource.Success<Bill>(data = bill)
        } catch (e: Exception) {
            Resource.Error<Bill>(message = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun insertBill(bill: Bill): Resource<Bill> {
        return try {
            dao.insertBill(bill.toBillEntity())
            Resource.Success<Bill>(data = bill)
        } catch (e: Exception) {
            Resource.Error<Bill>(message = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun deleteBill(bill: Bill) {
        dao.deleteBill(bill.toBillEntity())
    }
}