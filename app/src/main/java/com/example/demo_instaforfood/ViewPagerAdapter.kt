package com.example.demo_instaforfood

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo_instaforfood.Fragments.CategoryFragment.CategoryFragment
import com.example.demo_instaforfood.Fragments.ClientFragment.ClientFragment
import com.example.demo_instaforfood.Fragments.MapFragment.MapFragment
import com.example.demo_instaforfood.Fragments.ReviewsFragment.ReviewsFragment
import com.example.demo_instaforfood.Fragments.DownloadFragment.DownloadFragment

private val NUM_TABS = 6

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReviewsFragment()
            1 -> MapFragment()
            2 -> CategoryFragment()
            3 -> CategoryFragment()
            4 -> ClientFragment()
            5 -> DownloadFragment()
            else -> ReviewsFragment()
        }
    }

    
    
}