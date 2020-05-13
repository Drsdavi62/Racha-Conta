package com.example.application.billsplitingapp.database.product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(product: ProductModel) : Long

    @Query("SELECT * FROM ProductModel")
    fun getProductList() : LiveData<List<ProductModel>>

    @Query("DELETE FROM ProductModel WHERE id = :id")
    suspend fun deleteProduct(id: java.lang.Integer)

    @Query("UPDATE ProductModel SET name = :name, price =:price WHERE id = :id")
    suspend fun editProduct(id: java.lang.Integer, name: String, price: Float)

    @Query("UPDATE ProductModel SET name = :name WHERE id = :id")
    suspend fun editProductName(id : Int, name : String)

    @Query("UPDATE ProductModel SET price = :price WHERE id = :id")
    suspend fun editProductPrice(id : Int, price : Float)

    @Query("SELECT * FROM ProductModel WHERE id =:id")
    suspend fun getProduct(id : Int) : ProductModel

    @Query("SELECT * FROM ProductModel WHERE id = (SELECT MAX(id)  FROM ProductModel)")
    suspend fun getLastProduct() : ProductModel

}