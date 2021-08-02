package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PersonModel (var name : String, var value : Float, var billId : Int){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}