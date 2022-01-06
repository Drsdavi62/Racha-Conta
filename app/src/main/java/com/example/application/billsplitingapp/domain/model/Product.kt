package com.example.application.billsplitingapp.domain.model

data class Product(
    var id: Int = 0,
    var billId: Int,
    var name: String,
    var value: Float,
    var people: List<Person>
)