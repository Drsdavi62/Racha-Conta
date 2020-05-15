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

    fun getList() : LiveData<List<RelationModel>>{
        return dao.getRelationList()
    }

    suspend fun insertRelation(productId: Integer, personId: Integer, value: Float) {
        dao.insertRelation(RelationModel(productId, personId, value))
    }

    suspend fun deleteByProduct(productId: Integer) {
        dao.deleteByProduct(productId)
    }

    fun deleteByPerson(personId: Integer) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteByPerson(personId)
        }
    }

    fun deleteSpecific(productId: Int, personId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteSpecific(productId, personId)
        }
    }

    fun setRelationValue(productId: Integer, value: Float) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.setRelationValue(productId.toInt(), value)
        }
    }

    fun getProductsRelated(personId: Integer) = runBlocking {
        return@runBlocking dao.getProductsRelated(personId)
    }

    fun getPeopleRelated(productId: Integer): List<Int> = runBlocking {
        return@runBlocking dao.getPeopleRelated(productId)
    }

    fun getRelationValue(personId: Integer): List<Float> = runBlocking {
        return@runBlocking dao.getRelationValue(personId)
    }

}