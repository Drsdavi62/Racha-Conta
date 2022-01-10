package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Query("SELECT * FROM billentity")
    fun getBills(): Flow<List<BillEntity>>

    @Query("SELECT * FROM billentity WHERE id = :id")
    suspend fun getBillById(id: Int): BillEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(billEntity: BillEntity): Long

    @Delete
    suspend fun deleteBill(billEntity: BillEntity)
}