package com.example.application.billsplitingapp.newProductDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.PersonModel

class ProductDialogAdapter(var list: List<PersonModel>, var selectedId: MutableList<Int>?) : RecyclerView.Adapter<ProductDialogAdapter.ViewHolder>() {

    init{
        if(selectedId == null){
            selectedId = ArrayList()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name

        if(selectedId!!.contains(list[position].id)){
            holder.checkBox.isChecked = true
        }

        holder.checkBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            if(b){
                selectedId!!.add(list[position].id)
            } else {
                selectedId!!.remove(list[position].id)
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox : CheckBox = itemView.findViewById(R.id.dialog_item_checkbox)
        val name : TextView = itemView.findViewById(R.id.dialog_item_name)
    }
}