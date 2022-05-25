package com.example.application.billsplitingapp.database.bill

import android.content.Context
import com.example.application.billsplitingapp.database.person.PersonDao
import com.example.application.billsplitingapp.database.person.PersonDatabase
import com.example.application.billsplitingapp.models.BillModel

class BillRepository (private val context: Context) {

    private val billDao : BillDao

    init{
        val database = BillDatabase.getDatabase(context)
        billDao = database.billDao()
    }

    fun getList() = billDao.getList()

    suspend fun insertBill(name : String, date : String): Long {
        return billDao.insertBill(BillModel(name, date))
    }
    suspend fun editBill(id : Int, name : String){
        billDao.editBill(id, name)
    }

    suspend fun deleteBill(id: Int){
        billDao.deleteBill(id)
    }

    suspend fun deleteMultipleBills(ids: List<Int>) {
        billDao.deleteMultipleBills(ids)
    }

    suspend fun setBillValue(id : Int, value : Float){
        billDao.setBillValue(id, value)
    }
}