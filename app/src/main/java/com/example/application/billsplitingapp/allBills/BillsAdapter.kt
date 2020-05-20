package com.example.application.billsplitingapp.allBills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.BillModel
import org.w3c.dom.Text
import java.util.zip.Inflater

class BillsAdapter(val list : List<BillModel>) : RecyclerView.Adapter<BillsAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemCLick(id: Int, name : String)
    }

    private lateinit var listener : OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){this.listener = listener}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.date.text = list[position].date

    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById<TextView>(R.id.product_name)
        val date : TextView = itemView.findViewById(R.id.product_price)

        init{
            date.setOnClickListener{
                listener.onItemCLick(list[adapterPosition].id, list[adapterPosition].name)
            }
            itemView.setOnClickListener{
                listener.onItemCLick(list[adapterPosition].id, list[adapterPosition].name)
            }
        }
    }
}