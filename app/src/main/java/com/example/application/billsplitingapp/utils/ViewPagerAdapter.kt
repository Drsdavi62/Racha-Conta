package com.example.application.billsplitingapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.application.billsplitingapp.peopleList.PersonListFragment
import com.example.application.billsplitingapp.productList.ProductListFragment

class ViewPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ProductListFragment()
            1 -> PersonListFragment()
            else -> ProductListFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}