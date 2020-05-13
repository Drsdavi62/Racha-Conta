package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductModel (var name : String, var price : Float, var amount : Int) {
    @PrimaryKey(autoGenerate = true)
    lateinit var id : Integer
}