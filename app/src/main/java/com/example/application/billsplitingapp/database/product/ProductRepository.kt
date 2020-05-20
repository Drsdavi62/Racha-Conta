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

    suspend fun editProduct(id: Integer, name: String, price: Float, amount: Int) {
        productDao.editProduct(id, name, price, amount)
    }

    suspend fun addAmount(id: Integer) {
        productDao.addAmount(id)
    }

    fun getList(billId: Int): LiveData<List<ProductModel>> {
        return productDao.getProductList(billId)
    }

    suspend fun getProduct(id: Integer): ProductModel {
        return productDao.getProduct(id.toInt())
    }

    suspend fun deleteProduct(id: Integer) {
        productDao.deleteProduct(id)
    }

    suspend fun getLastProduct(): ProductModel {
        return productDao.getLastProduct()
    }
}