package com.example.application.billsplitingapp.productList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import com.example.application.billsplitingapp.utils.Formatter
import kotlinx.android.synthetic.main.item_product.view.*


class ProductListAdapter(
    val productList: MutableList<ProductModel>,
    var relationList: List<List<PersonModel>>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    lateinit var listener: OnItemClickListener
    private var selectionMode = false
    var selectedItems: MutableList<ProductModel> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(position: Int)

        fun onHold(itemView: View)

        fun returnMode()

        fun onAddClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = productList[position].name
        holder.price.text = Formatter.currencyFormat(productList[position].price)
//        val personList = relationList[position] as MutableList
//        personList.sortBy { it.id.toInt() }
//        val names = personList.map { if(it != null) it.name else "Foi mal" }
//        holder.people.text = names.joinToString(", ")
        holder.amount.text = productList[position].amount.toString()
        holder.addBtn.setOnClickListener {
            listener.onAddClick(position)
        }
    }

    fun updateList(newList: List<ProductModel>, relationList: List<List<PersonModel>>) {
        this.relationList = relationList
        val diffCallback = ProductDiffCallback(this.productList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.productList.clear()
        this.productList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.bill_name
        var price: TextView = itemView.item_bill_value
        var people: TextView = itemView.item_bill_people
        var amount: TextView = itemView.item_bill_date
        var addBtn: ImageButton = itemView.product_add_button

        init {
            itemView.setOnClickListener {
                if (!selectionMode) {
                    listener.onItemClick(adapterPosition)
                } else {
                    if (!selectedItems.contains(productList[adapterPosition])) {
                        selectedItems.add(productList[adapterPosition])
                        itemView.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.colorAccent
                            )
                        )
                    } else {
                        selectedItems.remove(productList[adapterPosition])
                        itemView.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.cardBackgroundColor
                            )
                        )
                        if (selectedItems.isEmpty()) {
                            selectionMode = false
                            listener.returnMode()
                        }
                    }
                }
            }

            itemView.setOnLongClickListener {
                selectionMode = true
                selectedItems.add(productList[adapterPosition])
                listener.onHold(itemView)
                return@setOnLongClickListener true
            }

            amount.setOnClickListener { listener.onItemClick(adapterPosition) }
        }

    }
}