package com.example.application.billsplitingapp.allBills

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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
    private var deletionMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_all_bills)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.actionBarColor)))
        supportActionBar?.title = getString(R.string.all_bills_action_bar)
        viewModel = ViewModelProvider(this).get(AllBillsViewModel::class.java)
        recyclerView = findViewById(R.id.all_bills_recycler)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = bill_add_button

        viewModel.list.observe(this, Observer {

            findViewById<TextView>(R.id.bill_empty_alert).visibility = if(it.isEmpty()) View.VISIBLE else View.GONE

            val relationList = viewModel.getRelationList(it)
            adapter = BillsAdapter(it, relationList)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : BillsAdapter.OnItemClickListener{
                override fun onItemCLick(id: Int, name : String) {
                    val prefs = PreferenceManager.getDefaultSharedPreferences(application)
                    val editor = prefs.edit()
                    editor.putInt(Constants.BILL_ID, id)
                    editor.apply()
                    val intent = Intent(this@AllBillsActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra(Constants.BILL_NAME, name)
                    startActivity(intent)
                }

                override fun onHold(itemView: View) {
                    deletionMode = true
                    fab.setImageDrawable(ContextCompat.getDrawable(this@AllBillsActivity, R.drawable.ic_delete))
                }

                override fun onReturnMode() {
                    deletionMode = false
                    fab.setImageDrawable(ContextCompat.getDrawable(this@AllBillsActivity, R.drawable.ic_add))
                }
            })

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0){
                    fab.hide()
                } else {
                    fab.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        fab.setOnClickListener{
            if(!deletionMode) {
                val inputDialog = InputDialog(this, getString(R.string.new_bill_dialog_title))
                inputDialog.show(View.OnClickListener {
                    if (inputDialog.editText.isNotEmpty()) {
                        viewModel.insertBill(inputDialog.editText)
                        this@AllBillsActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                        inputDialog.dismiss()
                    } else {
                        Toast.makeText(this, getString(R.string.empty_bill_name_toast), Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                adapter.selectedItems.forEach {
                    viewModel.deleteBill(it.id)
                }
                deletionMode = false
                adapter.selectedItems.clear()
                fab.setImageDrawable(ContextCompat.getDrawable(this@AllBillsActivity, R.drawable.ic_add))
            }
        }
    }
}
