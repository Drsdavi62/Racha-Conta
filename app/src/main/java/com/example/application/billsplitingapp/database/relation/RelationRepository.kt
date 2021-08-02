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

    suspend fun insertRelation(productId: Int, personId: Int, value: Float) {
        dao.insertRelation(RelationModel(productId, personId, value))
    }

    suspend fun deleteByProduct(productId: Int) {
        dao.deleteByProduct(productId)
    }

    suspend fun deleteByPerson(personId: Int) {
        dao.deleteByPerson(personId)
    }

    suspend fun deleteSpecific(productId: Int, personId: Int) {
        dao.deleteSpecific(productId, personId)
    }

    suspend fun setRelationValue(productId: Int, value: Float) {
        dao.setRelationValue(productId.toInt(), value)
    }

    suspend fun getProductsRelated(personId: Int): List<Int> {
        return dao.getProductsRelated(personId)
    }

    suspend fun getPeopleRelated(productId: Int): List<Int>  {
        return dao.getPeopleRelated(productId)
    }

    suspend fun getRelationValue(personId: Int): List<Float>  {
        return dao.getRelationValue(personId)
    }

}