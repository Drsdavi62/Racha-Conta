package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.FullProductEntity
import com.example.application.billsplitingapp.data.cache.model.ProductWithPeopleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductWithPeopleDao {

    @Query("SELECT * FROM productentity WHERE billId = :billId")
    fun getProductsFromBill(billId: Int): Flow<List<FullProductEntity>>

    @Query("SELECT * FROM productentity WHERE id = :id")
    suspend fun getProductById(id: Int): FullProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductWithPeople(productWithPeople: ProductWithPeopleEntity)

    @Query("DELETE FROM productwithpeopleentity WHERE productId = :productId")
    suspend fun deleteRelationsForProduct(productId: Int)

    @Query("DELETE FROM productwithpeopleentity WHERE productId = :personId")
    suspend fun deleteRelationsForPerson(personId: Int)
}