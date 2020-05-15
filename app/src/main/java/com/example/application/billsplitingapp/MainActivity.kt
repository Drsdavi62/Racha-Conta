package com.example.application.billsplitingapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.application.billsplitingapp.peopleList.PersonListFragment
import com.example.application.billsplitingapp.utils.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager: ViewPager
    private lateinit var vpAdapter: ViewPagerAdapter
    private lateinit var sharedViewModel: SharedViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewPager = findViewById(R.id.view_pager)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        vpAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = vpAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //var previous = 0f
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
//                if(positionOffset > previous && previous != 0f && positionOffset != 0f){
//                    viewPager.setCurrentItem(1, false)
//                    previous = 0f
//                } else if (positionOffset < previous && previous != 0f && positionOffset != 0f){
//                    viewPager.setCurrentItem(0, false)
//                    previous = 0f
//                }
//                previous = positionOffset
            }

            override fun onPageSelected(position: Int) {
                if (position == 1) {

                    sharedViewModel.setup()
                } else {

                    sharedViewModel.setupProduct()
                }
                bottomNavigationView.selectedItemId =
                    if (position == 0) R.id.bottom_navigation_products else R.id.bottom_navigation_people
            }
        })


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_navigation_products -> {
//                    Navigation.findNavController(this, R.id.products_fragment)
//                        .navigate(R.id.action_personListFragment_to_productListFragment)
                    viewPager.setCurrentItem(0, true)
                    true
                }
                R.id.bottom_navigation_people -> {
//                    Navigation.findNavController(this, R.id.products_fragment)
//                        .navigate(R.id.action_productListFragment_to_personListFragment)
                    viewPager.setCurrentItem(1, true)
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
