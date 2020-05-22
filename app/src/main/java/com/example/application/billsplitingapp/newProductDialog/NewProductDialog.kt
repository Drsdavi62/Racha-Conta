package com.example.application.billsplitingapp.newProductDialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.PersonModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class NewProductDialog (context: Context, val list : List<PersonModel>) {

    var name : TextInputEditText
    var price : CurrencyEditText
    var amount : NumberPicker
    private val factory: LayoutInflater = LayoutInflater.from(context)
    private val dialogView: View = factory.inflate(R.layout.dialog, null)
    private val alertDialog : AlertDialog = AlertDialog.Builder(context).setTitle(context.getString(
        R.string.product_dialog_title)).create()
    var recyclerView: RecyclerView
    private var personLayout : ConstraintLayout
    lateinit var adapter: ProductDialogAdapter

    init {
        alertDialog.setView(dialogView)
        name = dialogView.findViewById(R.id.dialog_product_name_value)
        price = dialogView.findViewById(R.id.dialog_product_price_value)

        amount = dialogView.findViewById(R.id.dialog_np)
        amount.minValue = 1
        amount.maxValue = 99
        amount.wrapSelectorWheel = true
        recyclerView = dialogView.findViewById(R.id.dialog_recycler)
        personLayout = dialogView.findViewById(R.id.dialog_person_layout)
        if(list.isNotEmpty()) {
            personLayout.visibility = View.VISIBLE
            adapter =
                ProductDialogAdapter(
                    list,
                    null
                )
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        } else {
            personLayout.visibility = View.GONE
        }
    }

    constructor(context: Context, list: List<PersonModel>, idList: MutableList<Int>) : this(context, list) {
        alertDialog.setView(dialogView)
        recyclerView = dialogView.findViewById(R.id.dialog_recycler)
        personLayout = dialogView.findViewById(R.id.dialog_person_layout)
        if(list.isNotEmpty()) {
            personLayout.visibility = View.VISIBLE
            adapter =
                ProductDialogAdapter(
                    list,
                    idList
                )
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        } else {
            personLayout.visibility = View.GONE
        }

    }

    var amountInt : Int?
        get() {
            return amount.value
        }
        set(value){
            if (value != null) {
                amount.value = value
            }
        }

    var nameStr: String?
        get() = name.text.toString().trim { it <= ' ' }
        set(text) {
            name.setText(text)
        }

    var priceFloat : Float?
        get() {
            return price.getFloatValue()
        }
        set(float) {
            price.setFloatValue(float!!)
        }

    fun getSelected() : List<Int>{
        return if(list.isEmpty()){
            ArrayList()
        } else {
            adapter.selectedId!!
        }
    }

    fun dismiss(){
        alertDialog.dismiss()
    }

    fun show(listener: View.OnClickListener){
        dialogView.findViewById<MaterialButton>(R.id.dialog_confirm_button).setOnClickListener(listener)
        alertDialog.show()
    }
}