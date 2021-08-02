package com.example.application.billsplitingapp.utils.retrofit

import com.example.application.billsplitingapp.models.ProductModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiInterface {
    @GET("orders/{user_id}/{table_id}")
    fun getOrders(@Path("user_id") id : Int, @Path("table_id") table_id : Int) : Call<List<ProductModel>>
}