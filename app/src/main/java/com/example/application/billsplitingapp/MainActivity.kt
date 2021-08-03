package com.example.application.billsplitingapp

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra(Constants.BILL_NAME)!!
//        actionBar.setBackgroundDrawable(
//            ColorDrawable(
//                ContextCompat.getColor(
//                    this,
//                    R.color.actionBarColor
//                )
//            )
//        )
        actionBar.setDisplayHomeAsUpEnabled(true)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        NavigationUI.setupWithNavController(
            bottomNavigationView,
            Navigation.findNavController(this, R.id.products_fragment)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
