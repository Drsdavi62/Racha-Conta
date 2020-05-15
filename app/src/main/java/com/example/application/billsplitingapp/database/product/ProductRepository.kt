package com.example.application.billsplitingapp.database.product

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductRepository (private val context: Context){

    private val productDao : ProductDao

    init{
        val database = ProductDatabase.getDatabase(context)
        productDao = database.productDao()
    }

    fun getRawList() = runBlocking {
        return@runBlocking productDao.getRawList()
    }

    suspend fun insertProduct(productModel: ProductModel){
        productDao.add(productModel)
    }

    suspend fun editProduct(id: Integer, name: String, price: Float, amount : Int){
        productDao.editProduct(id, name, price, amount)
    }

    fun addAmount(id : Integer){
        CoroutineScope(Dispatchers.IO).launch {
            productDao.addAmount(id)
        }
    }

    fun getList() : LiveData<List<ProductModel>>{
        return productDao.getProductList()
    }

    fun getProduct(id : Integer) = runBlocking {
        return@runBlocking productDao.getProduct(id.toInt())
    }

    fun deleteProduct(id : Integer){
        CoroutineScope(Dispatchers.IO).launch {
            productDao.deleteProduct(id)
        }
    }

    suspend fun getLastProduct() : ProductModel {
        return productDao.getLastProduct()
    }
}