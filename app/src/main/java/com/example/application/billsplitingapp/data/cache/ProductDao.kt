package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM productentity WHERE billId = :billId")
    suspend fun getProductsFromBill(billId: Int): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)
}