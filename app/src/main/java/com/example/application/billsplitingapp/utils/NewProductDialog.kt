package com.example.application.billsplitingapp.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.NumberPicker
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.PersonModel
import com.google.android.material.button.MaterialButton
import me.abhinay.input.CurrencyEditText
import org.jetbrains.anko.find
import java.text.DecimalFormat

class NewProductDialog (private val context: Context, val list : List<PersonModel>) {

    constructor(context: Context, list: List<PersonModel>, idList: MutableList<Integer>) : this(context, list) {
        alertDialog.setView(dialogView)
        name = dialogView.findViewById<EditText>(R.id.dialog_product_name)
        price = dialogView.findViewById<CurrencyEditText>(R.id.dialog_product_price)
        recyclerView = dialogView.findViewById(R.id.dialog_recycler)

        personLayout = dialogView.findViewById(R.id.dialog_person_layout)
        if(list.isNotEmpty()) {
            personLayout.visibility = View.VISIBLE
            adapter = ProductDialogAdapter(list, idList)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        } else {
            personLayout.visibility = View.GONE
        }

    }

    var name : EditText? = null
    var price : CurrencyEditText? = null
    lateinit var amount : NumberPicker
    private val factory: LayoutInflater = LayoutInflater.from(context)
    private val dialogView: View = factory.inflate(R.layout.dialog, null)
    private val alertDialog : AlertDialog = AlertDialog.Builder(context).setTitle(context.getString(
            R.string.product_dialog_title)).create()
    var recyclerView: RecyclerView
    private var personLayout : ConstraintLayout
    lateinit var adapter: ProductDialogAdapter

    init {
        alertDialog.setView(dialogView)
        name = dialogView.findViewById<EditText>(R.id.dialog_product_name)
        price = dialogView.findViewById<CurrencyEditText>(R.id.dialog_product_price)
        amount = dialogView.findViewById(R.id.dialog_np)
        amount.minValue = 1
        amount.maxValue = 99
        amount.wrapSelectorWheel = true
        recyclerView = dialogView.findViewById(R.id.dialog_recycler)
        personLayout = dialogView.findViewById(R.id.dialog_person_layout)
        if(list.isNotEmpty()) {
            personLayout.visibility = View.VISIBLE
            adapter = ProductDialogAdapter(list, null)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        } else {
            personLayout.visibility = View.GONE
        }
    }

    fun show(listener: View.OnClickListener){
        dialogView.findViewById<MaterialButton>(R.id.dialog_confirm_button).setOnClickListener(listener)
        name!!.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        alertDialog.show()
    }

    var amountInt : Int?
        get() {
            return amount!!.value
        }
        set(value){
            if (value != null) {
                amount!!.value = value
            }
        }

    var nameStr: String?
        get() = name?.text.toString().trim { it <= ' ' }
        set(text) {
            name?.setText(text)
        }

    var priceFloat : Float?
        get() {
            var p = price?.text.toString().trim()
            return if(p.isNotEmpty()) {
                p = p.replace(",", "")
                p = p.replace(".", "")
                p = p.substring(0, p.length - 2) + "." + p.substring(p.length-2, p.length)
                p.toFloat()
            }else {
                0f
            }
        }
        set(float) {
            var f = float!!
            if (f - f.toInt() == 0f){
                f = float?.times(10f)
            }
            price?.setText(f.toString())
        }

    fun getSelected() : List<Integer>{
        return if(list.isEmpty()){
            ArrayList()
        } else {
            adapter.selectedId!!
        }
    }

    fun dismiss(){
        alertDialog.dismiss()
    }
}