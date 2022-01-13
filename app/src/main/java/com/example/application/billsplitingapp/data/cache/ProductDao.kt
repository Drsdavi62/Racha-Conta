package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM productentity WHERE billId = :billId")
    fun getProductsFromBill(billId: Int): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Query("UPDATE productentity SET amount = :amount WHERE id = :id")
    suspend fun updateAmount(id: Int, amount: Int)
}