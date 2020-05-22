package com.example.application.billsplitingapp.peopleList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.database.person.PersonRepository
import com.example.application.billsplitingapp.database.product.ProductRepository
import com.example.application.billsplitingapp.database.relation.RelationRepository
import com.example.application.billsplitingapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonListViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application)
    private val relationRepository = RelationRepository(application)
    private val productRepository = ProductRepository(application)
    val prefs = PreferenceManager.getDefaultSharedPreferences(application)
    var list = personRepository.getList(prefs.getInt(Constants.BILL_ID, 0))

    fun insertPerson(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            personRepository.insertPerson(
                name, 0f, prefs.getInt(
                    Constants.BILL_ID, 0
                )
            )
        }
    }

    fun setValue(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var value: Float = 0f
            relationRepository.getRelationValue(id).forEach { float ->
                value += float
            }
            personRepository.setValue(id, value)
        }
    }

    fun deletePerson(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            relationRepository.getProductsRelated(id).forEach { productId ->
                val product = productRepository.getProduct(productId)
                var value = (product.price * product.amount) / (relationRepository.getPeopleRelated(productId).size - 1).toFloat()
                relationRepository.setRelationValue(productId, value)
            }
            relationRepository.deleteByPerson(id)
            personRepository.getRawList(prefs.getInt(Constants.BILL_ID, 0)).forEach { person ->
                var finalValue = 0f
                relationRepository.getRelationValue(person.id).forEach { value ->
                    finalValue += value
                }
                personRepository.setValue(person.id, finalValue)
            }
            personRepository.deletePerson(id)
//        setUp()
        }
    }

    fun editPerson(id: Int, name: String) {
        CoroutineScope(Dispatchers.IO).launch { personRepository.editPerson(id, name) }

    }

    fun setUp() {
        CoroutineScope(Dispatchers.IO).launch {
            personRepository.getRawList(prefs.getInt(Constants.BILL_ID, 0)).forEach { person ->
                var finalValue = 0f
                relationRepository.getRelationValue(person.id).forEach { value ->
                    finalValue += value
                }
                personRepository.setValue(person.id, finalValue)
            }
        }
    }
}
