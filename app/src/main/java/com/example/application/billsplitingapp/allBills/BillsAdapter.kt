package com.example.application.billsplitingapp.allBills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.BillModel
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.utils.Formatter
import kotlinx.coroutines.selects.select
import java.util.*

class BillsAdapter(val list : List<BillModel>, private val relationList : List<List<PersonModel>>) : RecyclerView.Adapter<BillsAdapter.ViewHolder>() {

    private lateinit var listener : OnItemClickListener
    private var selectionMode = false
    var selectedItems : MutableList<BillModel> = ArrayList()

    interface OnItemClickListener{
        fun onItemCLick(id: Int, name : String)
        fun onHold(itemView: View)
        fun onReturnMode()
    }

    fun setOnItemClickListener(listener: OnItemClickListener){this.listener = listener}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.date.text = list[position].date
        holder.value.text = Formatter.currencyFormat(list[position].value)
        val personList = relationList[position]
        val nameList = personList.map { it.name }
        var str = ""
        for(i in nameList.indices){
            if(i != nameList.size-1){
                str += nameList[i] + "\n"
            } else {
                str += nameList[i]
            }
        }
        holder.people.text = str
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.bill_name)
        val value : TextView = itemView.findViewById(R.id.item_bill_value)
        val date : TextView = itemView.findViewById(R.id.item_bill_date)
        val people: TextView = itemView.findViewById(R.id.item_bill_people)

        init{
//            name.setOnClickListener{
//                listener.onItemCLick(list[adapterPosition].id, list[adapterPosition].name)
//            }
//            date.setOnClickListener{
//                listener.onItemCLick(list[adapterPosition].id, list[adapterPosition].name)
//            }

            itemView.setOnClickListener{
                if(!selectionMode) {
                    listener.onItemCLick(list[adapterPosition].id, list[adapterPosition].name)
                } else {
                    if(selectedItems.contains(list[adapterPosition])){
                        selectedItems.remove(list[adapterPosition])
                        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.cardBackgroundColor))
                        if(selectedItems.isEmpty()){
                            listener.onReturnMode()
                        }
                    } else {
                        selectedItems.add(list[adapterPosition])
                        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
                    }
                }
            }

            itemView.setOnLongClickListener{
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
                selectionMode = true
                selectedItems.add(list[adapterPosition])
                listener.onHold(itemView)
                return@setOnLongClickListener true
            }
        }
    }
}
