package com.example.application.billsplitingapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
