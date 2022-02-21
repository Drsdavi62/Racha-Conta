package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.FullPersonEntity
import com.example.application.billsplitingapp.data.cache.model.FullProductEntity
import com.example.application.billsplitingapp.data.cache.model.ProductWithPeopleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductWithPeopleDao {

    @Query("SELECT * FROM productentity WHERE billId = :billId")
    fun getProductsFromBill(billId: Int): Flow<List<FullProductEntity>>

    @Query("SELECT * FROM personentity WHERE billId = :billId")
    fun getPeopleFromBill(billId: Int): Flow<List<FullPersonEntity>>

    @Query("SELECT * FROM productentity WHERE id = :id")
    suspend fun getProductById(id: Int): FullProductEntity?

    @Query("SELECT * FROM personentity WHERE id = :id")
    suspend fun getPersonById(id: Int): FullPersonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductWithPeople(productWithPeople: ProductWithPeopleEntity)

    @Query("DELETE FROM productwithpeopleentity WHERE productId = :productId")
    suspend fun deleteRelationsForProduct(productId: Int)

    @Query("DELETE FROM productwithpeopleentity WHERE personId = :personId")
    suspend fun deleteRelationsForPerson(personId: Int)

    @Query("SELECT COUNT(productId) FROM productwithpeopleentity WHERE productId = :productId")
    suspend fun getRelationAmountForProduct(productId: Int): Int
}