package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BillModel (var name : String, var date : String){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var value : Float = 0f
}