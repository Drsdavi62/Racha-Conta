package com.example.application.billsplitingapp.domain.model

data class Bill(
    var id: Int = 0,
    var name: String,
    var value: Float,
    var date: String,
    var products: List<Product>,
    var people: List<Person>
)