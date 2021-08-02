package com.example.application.billsplitingapp.peopleList

import androidx.recyclerview.widget.DiffUtil
import com.example.application.billsplitingapp.models.PersonModel

class PersonDiffCallback (private val oldList : List<PersonModel>, private val newList : List<PersonModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                 oldList[oldItemPosition].value == newList[newItemPosition].value
    }
}