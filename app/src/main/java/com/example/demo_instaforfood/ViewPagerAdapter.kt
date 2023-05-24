package com.example.demo_instaforfood

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo_instaforfood.Fragments.CategoryFragment.CategoryFragment
import com.example.demo_instaforfood.Fragments.ClientFragment.ClientFragment
import com.example.demo_instaforfood.Fragments.MapFragment.MapFragment
import com.example.demo_instaforfood.Fragments.ReviewsFragment.ReviewsFragment

private val NUM_TABS = 5

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ReviewsFragment()
            1 -> return MapFragment()
            2 -> return CategoryFragment()
            3 -> return CategoryFragment()
            4 -> return ClientFragment()
        }
        return ReviewsFragment()
    }

    
    
}