package com.example.application.billsplitingapp.allBills

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.billsplitingapp.MainActivity
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.utils.Constants
import com.example.application.billsplitingapp.utils.InputDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_all_bills.*

class AllBillsActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: BillsAdapter
    private lateinit var viewModel: AllBillsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_bills)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.actionBarColor)))
        viewModel = ViewModelProvider(this).get(AllBillsViewModel::class.java)
        recyclerView = findViewById(R.id.all_bills_recycler)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.list.observe(this, Observer {
            adapter = BillsAdapter(it)
            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : BillsAdapter.OnItemClickListener{
                override fun onItemCLick(id: Int, name : String) {
//                    val inputDialog = InputDialog(this@AllBillsActivity, "Edit Bill")
//                    inputDialog.show(View.OnClickListener {
//                        if(inputDialog.editText.isNotEmpty()){
//                            viewModel.editBill(id, inputDialog.editText)
//                            this@AllBillsActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//                            inputDialog.dismiss()
//                        } else {
//                            Toast.makeText(this@AllBillsActivity, "Can't be empty", Toast.LENGTH_LONG).show()
//                        }
//                    })

                    val prefs = PreferenceManager.getDefaultSharedPreferences(application)
                    val editor = prefs.edit()
                    editor.putInt(Constants.BILL_ID, id)
                    editor.apply()
                    val intent = Intent(this@AllBillsActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra(Constants.BILL_NAME, name)
                    startActivity(intent)
                }
            })

        })

        bill_add_button.setOnClickListener{
            val inputDialog = InputDialog(this, "New Bill")
            inputDialog.show(View.OnClickListener {
                if(inputDialog.editText.isNotEmpty()) {
                    viewModel.insertBill(inputDialog.editText)
                    this@AllBillsActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                    inputDialog.dismiss()
                } else {
                    Toast.makeText(this, "Can't be empty", Toast.LENGTH_LONG).show()
                }

            })
        }
    }
}
