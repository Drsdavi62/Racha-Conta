package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PersonModel (var name : String, var value : Float){
    @PrimaryKey(autoGenerate = true)
    lateinit var id : Integer
}