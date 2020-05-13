package com.example.application.billsplitingapp.peopleList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.application.billsplitingapp.database.person.PersonRepository
import com.example.application.billsplitingapp.database.product.ProductRepository
import com.example.application.billsplitingapp.database.relation.RelationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonListViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application)
    private val relationRepository = RelationRepository(application)
    private val productRepository = ProductRepository(application)
    var list = personRepository.getList()

    fun insertPerson(name : String){
        CoroutineScope(Dispatchers.IO).launch {personRepository.insertPerson(name, 0f)}
    }

    fun setValue(id : Integer){
        var value : Float = 0f
        relationRepository.getRelationValue(id).forEach{float ->
            value += float
        }
        personRepository.setValue(id, value)
    }

    fun deletePerson(id : Integer){
        relationRepository.getProductsRelated(id).forEach {productId ->
            var value = productRepository.getProduct(productId).price / (relationRepository.getPeopleRelated(productId).size - 1).toFloat()
            relationRepository.setRelationValue(productId, value)
        }
        relationRepository.deleteByPerson(id)
        personRepository.getRawList().forEach {person ->
            var finalValue = 0f
            relationRepository.getRelationValue(person.id).forEach { value ->
                finalValue += value
            }
            personRepository.setValue(person.id, finalValue)
        }
        personRepository.deletePerson(id)
//        setUp()
    }

    fun editPerson(id : Integer, name: String){
        personRepository.editPerson(id, name)
    }

    fun setUp(){
        personRepository.getRawList().forEach {person ->
            var finalValue = 0f
            relationRepository.getRelationValue(person.id).forEach { value ->
                finalValue += value
            }
            personRepository.setValue(person.id, finalValue)
        }
    }
}
