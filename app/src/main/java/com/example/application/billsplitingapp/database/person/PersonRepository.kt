package com.example.application.billsplitingapp.database.person

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.models.PersonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PersonRepository (private val context: Context){

    private val personDao : PersonDao

    init{
        val database = PersonDatabase.getDatabase(context)
        personDao = database.personDao()
    }

    suspend fun insertPerson(name : String, value : Float){
        personDao.add(PersonModel(name, value))
    }

    fun getList() : LiveData<List<PersonModel>> {
        return personDao.getPersonList()
    }

    fun getRawList() = runBlocking {
        return@runBlocking personDao.getRawPersonList()
    }

    fun getPerson(id: Int) = runBlocking {
        return@runBlocking personDao.getPerson(id)
    }

    fun setValue(id : Integer, value : Float){
        CoroutineScope(Dispatchers.IO).launch {
            personDao.setValue(value, id)
        }
    }

    fun deletePerson(id : Integer){
        CoroutineScope(Dispatchers.IO).launch{
            personDao.deletePerson(id)
        }
    }

    fun editPerson(id : Integer, name : String){
        CoroutineScope(Dispatchers.IO).launch {
            personDao.editPersonName(id, name)
        }
    }
}
