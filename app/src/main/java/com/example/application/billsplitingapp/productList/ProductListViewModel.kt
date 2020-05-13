package com.example.application.billsplitingapp.productList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.database.person.PersonRepository
import com.example.application.billsplitingapp.database.product.ProductRepository
import com.example.application.billsplitingapp.database.relation.RelationRepository
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application)
    private val relationRepository = RelationRepository(application)
    private val productRepository : ProductRepository = ProductRepository(application)
    var list : LiveData<List<ProductModel>>

    init{
        list = productRepository.getList()
    }

    fun insertProduct(product : ProductModel, idList : List<Integer>){
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.insertProduct(product)
            val finalProduct = productRepository.getLastProduct()
            if(idList.isNotEmpty()) {
                idList.forEach {
                    relationRepository.insertRelation(finalProduct.id, it, (product.price * product.amount) / idList.size)
                }
            }
        }
    }

    fun editProduct(id: Integer, name: String, price: Float, amount : Int, relationList: List<Integer>){
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.editProduct(id, name, price, amount)
            relationRepository.deleteByProduct(id)
            relationList.forEach{
                relationRepository.insertRelation(id, it, (price * amount) / relationList.size)
            }
        }
    }

    fun getPersonList() : List<PersonModel>{
        return personRepository.getRawList()
    }

    fun addRelation(idList : List<Integer>){
        CoroutineScope(Dispatchers.IO).launch {
            val product = productRepository.getLastProduct()
            idList.forEach {
                relationRepository.insertRelation(product.id, it, product.price / idList.size)
            }
        }
    }

    fun getRelations(productList : List<ProductModel>) : List<List<PersonModel>>{
        var finalList : MutableList<List<PersonModel>> = ArrayList()
        for(i in productList.indices){
            var personModelList : MutableList<PersonModel> = ArrayList()
            val relationList = relationRepository.getPeopleRelated(productList[i].id)
            relationList.forEach{ personId ->
                personModelList.add(personRepository.getPerson(personId))
            }
            finalList.add(personModelList)
        }
        return finalList
    }

    fun getOneRelation(productId: Integer) : List<Int>{
        return relationRepository.getPeopleRelated(productId)
    }

    fun deleteProduct(id : Integer){
        productRepository.deleteProduct(id)
        CoroutineScope(Dispatchers.IO).launch {
            relationRepository.deleteByProduct(id)
        }
    }
}
