package com.example.demo_instaforfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.demo_instaforfood.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

private val tabsArray = arrayOf(
    "Reviews",
    "Map",
    "Category",
    "Cities",
    "Clients"
)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabSetup()


    }

    private fun tabSetup() {
        val adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        binding.viewPager.adapter=adapter
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tlTabs, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()
    }
}