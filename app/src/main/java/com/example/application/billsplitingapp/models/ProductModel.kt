package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductModel (var name : String, var price : Float, var amount : Int, var billId : Int) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}