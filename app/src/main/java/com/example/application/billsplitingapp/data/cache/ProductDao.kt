package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM productentity WHERE billId = :billId")
    fun getProductsFromBill(billId: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM productentity WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity): Long

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Query("UPDATE productentity SET amount = :amount WHERE id = :id")
    suspend fun updateAmount(id: Int, amount: Int)
}