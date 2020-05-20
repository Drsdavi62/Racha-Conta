package com.example.application.billsplitingapp.database.relation

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.models.RelationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RelationRepository(private val context: Context) {

    private val dao: RelationDao

    init {
        val database = RelationDatabase.getDatabase(context)
        dao = database.relationDao()
    }

    fun getList(): LiveData<List<RelationModel>> {
        return dao.getRelationList()
    }

    suspend fun insertRelation(productId: Integer, personId: Integer, value: Float) {
        dao.insertRelation(RelationModel(productId, personId, value))
    }

    suspend fun deleteByProduct(productId: Integer) {
        dao.deleteByProduct(productId)
    }

    suspend fun deleteByPerson(personId: Integer) {
        dao.deleteByPerson(personId)
    }

    suspend fun deleteSpecific(productId: Int, personId: Int) {
        dao.deleteSpecific(productId, personId)
    }

    suspend fun setRelationValue(productId: Integer, value: Float) {
        dao.setRelationValue(productId.toInt(), value)
    }

    suspend fun getProductsRelated(personId: Integer): List<Integer> {
        return dao.getProductsRelated(personId)
    }

    suspend fun getPeopleRelated(productId: Integer): List<Int>  {
        return dao.getPeopleRelated(productId)
    }

    suspend fun getRelationValue(personId: Integer): List<Float>  {
        return dao.getRelationValue(personId)
    }

}