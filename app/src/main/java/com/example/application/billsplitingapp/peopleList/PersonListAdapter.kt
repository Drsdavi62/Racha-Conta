package com.example.application.billsplitingapp.peopleList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.productList.ProductDiffCallback

class PersonListAdapter(val list: MutableList<PersonModel>) :
    RecyclerView.Adapter<PersonListAdapter.ViewHolder>() {

    lateinit var listener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonListAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PersonListAdapter.ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.value.text = String.format("%.2f", list[position].value)
        holder.deleteButton.setOnClickListener{
            listener.onDeleteClick(position)
        }
    }

    fun updateList(newList : List<PersonModel>){
        val diffCallback = PersonDiffCallback(this.list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.person_name)
        val value: TextView = itemView.findViewById(R.id.person_value)
        val deleteButton : ImageButton = itemView.findViewById<ImageButton>(R.id.person_deleteButton)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}