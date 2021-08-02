package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class ProductModel (
    @SerializedName("name")
    var name : String,
    @SerializedName("price")
    var price : Float,

    var amount : Int,

    var billId : Int) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}