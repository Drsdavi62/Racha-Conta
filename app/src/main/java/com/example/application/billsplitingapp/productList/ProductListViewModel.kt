package com.example.application.billsplitingapp.productList

import android.app.Application
import android.content.SharedPreferences
import androidx.annotation.IntegerRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.database.bill.BillRepository
import com.example.application.billsplitingapp.database.person.PersonRepository
import com.example.application.billsplitingapp.database.product.ProductRepository
import com.example.application.billsplitingapp.database.relation.RelationRepository
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import com.example.application.billsplitingapp.utils.Constants
import com.example.application.billsplitingapp.utils.retrofit.ProductApiInterface
import com.example.application.billsplitingapp.utils.retrofit.Retrofitconfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application)
    private val relationRepository = RelationRepository(application)
    private val productRepository: ProductRepository = ProductRepository(application)
    private val billRepository = BillRepository(application)
    var list: LiveData<List<ProductModel>> = MutableLiveData()
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private val retrofitClient = Retrofitconfig.getRetrofitInstance(Constants.URL)

    fun getList(id : Int, table_id : Int) {
        list = productRepository.getList(prefs.getInt(Constants.BILL_ID, 0))
//        val endpoint = retrofitClient.create(ProductApiInterface::class.java)
//        val callback = endpoint.getOrders(id, table_id)
//
//        callback.enqueue(object : Callback<List<ProductModel>> {
//            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
//                print(t.toString())
//            }
//
//            override fun onResponse(
//                call: Call<List<ProductModel>>,
//                response: Response<List<ProductModel>>
//            ) {
//                list.value = response.body()
//            }
//
//        })
    }

    fun insertProduct(product: ProductModel, idList: List<Int>) {
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
        id: Int,
        name: String,
        price: Float,
        amount: Int,
        relationList: List<Int>
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

    fun getOneRelation(productId: Int): List<Int> = runBlocking {
        relationRepository.getPeopleRelated(productId)
    }

    fun deleteProduct(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.deleteProduct(id)
            relationRepository.deleteByProduct(id)
        }
    }

    fun setBillValue(sum: Float) {
        CoroutineScope(Dispatchers.IO).launch {
            billRepository.setBillValue(prefs.getInt(Constants.BILL_ID, 0), sum)
        }
    }
}
