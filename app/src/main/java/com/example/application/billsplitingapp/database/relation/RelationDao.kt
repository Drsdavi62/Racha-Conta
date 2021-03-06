package com.example.application.billsplitingapp.database.relation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.application.billsplitingapp.models.ProductModel
import com.example.application.billsplitingapp.models.RelationModel

@Dao
interface RelationDao {
    @Insert
    suspend fun insertRelation(relationModel: RelationModel) : Long

    @Query("SELECT * FROM RelationModel")
    fun getRelationList() : LiveData<List<RelationModel>>

    @Query("DELETE FROM RelationModel WHERE productId =:productId")
    suspend fun deleteByProduct(productId: Int)

    @Query("DELETE FROM RelationModel WHERE personId =:personId")
    suspend fun deleteByPerson(personId: Int)

    @Query("UPDATE RelationModel SET value = :value WHERE  productId =:productId")
    suspend fun setRelationValue(productId: Int, value : Float)

    @Query("DELETE FROM RelationModel WHERE productId =:productId AND personId =:personId ")
    suspend fun deleteSpecific(productId : Int, personId : Int)

    @Query("SELECT productId FROM RelationModel WHERE personId =:personId")
    suspend fun getProductsRelated(personId: Int) : List<Int>

    @Query("SELECT personId FROM RelationModel WHERE productId =:productId")
    suspend fun getPeopleRelated(productId: Int) : List<Int>

    @Query("SELECT value FROM RelationModel WHERE personId =:personId")
    suspend fun getRelationValue(personId: Int) : List<Float>
}