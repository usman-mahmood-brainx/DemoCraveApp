package com.example.demo_instaforfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.demo_instaforfood.Fragments.CategoryFragment.CategoryFragment
import com.example.demo_instaforfood.Fragments.CategoryFragment.DynamicFragment
import com.example.demo_instaforfood.Fragments.ClientFragment.ClientFragment
import com.example.demo_instaforfood.Fragments.DownloadFragment.DownloadFragment
import com.example.demo_instaforfood.Fragments.MapFragment.MapFragment
import com.example.demo_instaforfood.Fragments.ReviewsFragment.ReviewsFragment
import com.example.demo_instaforfood.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

private val tabsArray = arrayOf(
    "Reviews",
    "Map",
    "Category",
    "Cities",
    "Clients",
    "WorkManager"
)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabSetup()

    }

    private fun tabSetup() {
        
        for (tabName in tabsArray) {
            val tab = binding.tlTabs.newTab()
            tab.text = tabName
            binding.tlTabs.addTab(tab)
        }

        val initialFragment = ReviewsFragment()
        replaceFragment(initialFragment)

        binding.tlTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedFragment: Fragment = when(tab?.position){
                    0 -> ReviewsFragment()
                    1 -> MapFragment()
                    2 -> CategoryFragment()
                    3 -> CategoryFragment()
                    4 -> ClientFragment()
                    5 -> DownloadFragment()
                    else -> ReviewsFragment()
                }
                replaceFragment(selectedFragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.appfragmentContainer, fragment)
            .commit()
    }
}