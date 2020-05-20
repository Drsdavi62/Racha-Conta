package com.example.application.billsplitingapp.database.bill

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.application.billsplitingapp.models.BillModel

@Dao
interface BillDao {

    @Insert
    suspend fun insertBill(bill : BillModel)

    @Query("select * from BillModel")
    fun getList() : LiveData<List<BillModel>>

    @Query("DELETE FROM BillModel WHERE id = :id")
    suspend fun deleteBill(id : Int)

    @Query("UPDATE BillModel SET name =:name WHERE id =:id")
    suspend fun editBill(id : Int, name : String)
}