package com.example.application.billsplitingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.allBills.AllBillsActivity
import com.example.application.billsplitingapp.database.bill.BillRepository
import com.example.application.billsplitingapp.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private val billRepository = BillRepository(this)
    private lateinit var prefs: SharedPreferences

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra(Constants.BILL_NAME)!!
        actionBar.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.actionBarColor)))
        actionBar.setDisplayHomeAsUpEnabled(true)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_navigation_products -> {
                    Navigation.findNavController(this, R.id.products_fragment)
                        .navigate(R.id.action_personListFragment_to_productListFragment)
                    true
                }
                R.id.bottom_navigation_people -> {
                    Navigation.findNavController(this, R.id.products_fragment)
                        .navigate(R.id.action_productListFragment_to_personListFragment)
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.bottom_navigation_products -> {
                }
                R.id.bottom_navigation_people -> {
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, AllBillsActivity::class.java)
        startActivity(intent)
        return true
    }
}
