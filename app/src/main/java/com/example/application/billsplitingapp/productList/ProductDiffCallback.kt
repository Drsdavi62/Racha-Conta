package com.example.application.billsplitingapp.productList

import androidx.recyclerview.widget.DiffUtil
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel


class ProductDiffCallback (private val oldList : List<ProductModel>, private val newList: List<ProductModel>):  DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].price == newList[newItemPosition].price
    }

}