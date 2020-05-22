package com.example.application.billsplitingapp.database.person

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.models.PersonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PersonRepository(private val context: Context) {

    private val personDao: PersonDao

    init {
        val database = PersonDatabase.getDatabase(context)
        personDao = database.personDao()
    }

    suspend fun insertPerson(name: String, value: Float, billId: Int) {
        personDao.add(PersonModel(name, value, billId))
    }

    fun getList(billId: Int): LiveData<List<PersonModel>> {
        return personDao.getPersonList(billId)
    }

    suspend fun getRawList(billId: Int): List<PersonModel> {
        return personDao.getRawPersonList(billId)
    }

    suspend fun getPerson(id: Int): PersonModel {
        return personDao.getPerson(id)
    }

    suspend fun setValue(id: Int, value: Float) {
        personDao.setValue(value, id)
    }

    suspend fun deletePerson(id: Int) {
        personDao.deletePerson(id)
    }

    suspend fun deleteByBill(billId: Int){
        personDao.deleteByBill(billId)
    }

    suspend fun editPerson(id: Int, name: String) {
        personDao.editPersonName(id, name)
    }
}
