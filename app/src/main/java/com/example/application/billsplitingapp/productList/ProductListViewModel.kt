package com.example.application.billsplitingapp.productList

import android.app.Application
import androidx.annotation.IntegerRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.database.person.PersonRepository
import com.example.application.billsplitingapp.database.product.ProductRepository
import com.example.application.billsplitingapp.database.relation.RelationRepository
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import com.example.application.billsplitingapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application)
    private val relationRepository = RelationRepository(application)
    private val productRepository: ProductRepository = ProductRepository(application)
    var list: LiveData<List<ProductModel>>
    val prefs = PreferenceManager.getDefaultSharedPreferences(application)

    init {
        list = productRepository.getList(prefs.getInt(Constants.BILL_ID, 0))
    }

    fun insertProduct(product: ProductModel, idList: List<Integer>) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.insertProduct(product)
            val finalProduct = productRepository.getLastProduct()
            if (idList.isNotEmpty()) {
                idList.forEach {
                    relationRepository.insertRelation(finalProduct.id, it, (product.price * product.amount) / idList.size)
                }
            }
        }
    }

    fun editProduct(
        id: Integer,
        name: String,
        price: Float,
        amount: Int,
        relationList: List<Integer>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.editProduct(id, name, price, amount)
            relationRepository.deleteByProduct(id)
            relationList.forEach {
                relationRepository.insertRelation(id, it, (price * amount) / relationList.size)
            }
        }
    }

    fun getPersonList(): List<PersonModel> = runBlocking {
        return@runBlocking personRepository.getRawList(prefs.getInt(Constants.BILL_ID, 0))
    }

    fun addAmount(product: ProductModel, relationList: List<PersonModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            relationRepository.setRelationValue(
                product.id,
                (product.price * (product.amount + 1)) / relationList.size
            )
            productRepository.addAmount(product.id)
        }
    }

    fun getRelations(productList: List<ProductModel>): List<List<PersonModel>>  = runBlocking{
        var finalList: MutableList<List<PersonModel>> = ArrayList()
        for (i in productList.indices) {
            var personModelList: MutableList<PersonModel> = ArrayList()
            val relationList = relationRepository.getPeopleRelated(productList[i].id)
            relationList.forEach { personId ->
                personModelList.add(personRepository.getPerson(personId))
            }
            finalList.add(personModelList)
        }
        return@runBlocking finalList
    }

    fun getOneRelation(productId: Integer): List<Int> = runBlocking {
        relationRepository.getPeopleRelated(productId)
    }

    fun deleteProduct(id: Integer) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.deleteProduct(id)
            relationRepository.deleteByProduct(id)
        }
    }
}
