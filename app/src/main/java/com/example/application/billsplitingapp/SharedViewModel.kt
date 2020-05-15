package com.example.application.billsplitingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.application.billsplitingapp.database.person.PersonRepository
import com.example.application.billsplitingapp.database.product.ProductRepository
import com.example.application.billsplitingapp.database.relation.RelationRepository
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import com.example.application.billsplitingapp.models.RelationModel

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application)
    private val relationRepository = RelationRepository(application)
    private var productRepository: ProductRepository = ProductRepository(application)

    var personList : LiveData<List<PersonModel>>
    var productList : LiveData<List<ProductModel>>
    private var relationList : LiveData<List<RelationModel>>

    var observeList : MutableLiveData<List<ProductModel>> = MutableLiveData()

    init{
        personList = personRepository.getList()
        productList = productRepository.getList()
        relationList = relationRepository.getList()
    }


    fun setup() {
//        personRepository.getRawList().forEach { person ->
//            var finalValue = 0f
//            relationRepository.getRelationValue(person.id).forEach { value ->
//                finalValue += value
//            }
//            personRepository.setValue(person.id, finalValue)
//        }
        print("oi")
    }

    fun setupProduct(){
        observeList.value = productRepository.getRawList()
    }

}