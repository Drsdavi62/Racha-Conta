package com.example.application.billsplitingapp.utils

fun <T> MutableList<T>.addAllDistinct(list: List<T>) {
    val filteredList = list.filter { !this.contains(it) }
    this.addAll(filteredList)
}