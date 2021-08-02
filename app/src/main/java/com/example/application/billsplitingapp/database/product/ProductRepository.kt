package com.example.application.billsplitingapp.database.product

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductRepository(private val context: Context) {

    private val productDao: ProductDao

    init {
        val database = ProductDatabase.getDatabase(context)
        productDao = database.productDao()
    }

    suspend fun insertProduct(productModel: ProductModel) {
        productDao.add(productModel)
    }

    suspend fun getRawList(billId: Int) : List<ProductModel>{
        return productDao.getRawList(billId)
    }

    suspend fun editProduct(id: Int, name: String, price: Float, amount: Int) {
        productDao.editProduct(id, name, price, amount)
    }

    suspend fun addAmount(id: Int) {
        productDao.addAmount(id)
    }

    fun getList(billId: Int): LiveData<List<ProductModel>> {
        return productDao.getProductList(billId)
    }

    suspend fun getProduct(id: Int): ProductModel {
        return productDao.getProduct(id.toInt())
    }

    suspend fun deleteProduct(id: Int) {
        productDao.deleteProduct(id)
    }

    suspend fun deleteByBill(billId: Int){
        productDao.deleteByBill(billId)
    }

    suspend fun getLastProduct(): ProductModel {
        return productDao.getLastProduct()
    }
}