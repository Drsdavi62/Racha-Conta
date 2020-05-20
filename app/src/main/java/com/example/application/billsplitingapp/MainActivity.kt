package com.example.application.billsplitingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.application.billsplitingapp.allBills.AllBillsActivity
import com.example.application.billsplitingapp.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.test).setOnClickListener{
            val intent = Intent(this, AllBillsActivity::class.java)
            startActivity(intent)
        }

        supportActionBar!!.title = intent.getStringExtra(Constants.BILL_NAME)!!
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.actionBarColor)))
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
                    true
                }
                R.id.bottom_navigation_people -> {
                    true
                }
                else -> false
            }
        }
    }
}
