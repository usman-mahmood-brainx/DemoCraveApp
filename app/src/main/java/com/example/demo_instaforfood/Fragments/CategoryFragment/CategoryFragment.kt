package com.example.demo_instaforfood.Fragments.CategoryFragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.Models.Category
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.databinding.FragmentCategoryBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding

    private val INITIAL_FRAGMENT_INDEX = 0
    private val SLICE_SPACE = 1f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)

        val categoryList = dummylist()
        pieChartSetup(categoryList)
        reviewsListSetup(categoryList)
        dynamicTabsSetup(categoryList)

        return binding.root

    }

    private fun dummylist(): List<Category> {
        return listOf(
            Category("Brunch", 460, ContextCompat.getColor(requireContext(), R.color.brunch)),
            Category("Burgers", 61, ContextCompat.getColor(requireContext(), R.color.burgers)),
            Category("Pizza", 46, ContextCompat.getColor(requireContext(), R.color.pizza)),
            Category("Bangels", 28, ContextCompat.getColor(requireContext(), R.color.bangels)),
            Category("Tacos", 19, ContextCompat.getColor(requireContext(), R.color.tacos)),
            Category("Donuts", 17, ContextCompat.getColor(requireContext(), R.color.donuts)),
            Category("Coffee", 13, ContextCompat.getColor(requireContext(), R.color.coffe)),
            Category("Bar", 10, ContextCompat.getColor(requireContext(), R.color.bar))
        )
    }


    private fun reviewsListSetup(categoryList: List<Category>) {
        val categoryAdapter = CategoryAdapter(categoryList)
        binding.apply {
            rvCategories.layoutManager = LinearLayoutManager(requireContext())
            rvCategories.adapter = categoryAdapter
            rvCategories.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    private fun pieChartSetup(categoryList: List<Category>) {

        val sortedCategoryList = categoryList.sortedByDescending { it.totalReviws }
        var sum = 0
        val pieEntries = mutableListOf<PieEntry>()
        val colorList = mutableListOf<Int>()

        for (category in sortedCategoryList) {
            pieEntries.add(PieEntry(category.totalReviws.toFloat(), category.name))
            colorList.add(category.color)
            sum += category.totalReviws
        }

        val dataSet = PieDataSet(pieEntries, "").apply {
            colors = colorList
            sliceSpace = SLICE_SPACE
        }

        val piedata = PieData(dataSet).apply {
            setValueTextSize(0f)
            setValueTextColor(Color.WHITE)
        }

        binding.pieChart.apply {
            data = piedata
            description.isEnabled = false
            setDrawEntryLabels(false)
            legend.isEnabled = false
            isRotationEnabled = false
            setTouchEnabled(false)
            setHoleRadius(75f)
            animateY(1000, Easing.EaseInOutQuad)
            invalidate()
        }

        binding.tvSum.text = "$sum"

    }

    private fun dynamicTabsSetup(categoryList: List<Category>) {

        val fragmentList = mutableListOf<DynamicFragment>()
        for (category in categoryList) {
            val tab = binding.tbItemList.newTab()
            tab.text = "${category.name} (${category.totalReviws})"
            binding.tbItemList.addTab(tab)
            val fragment = DynamicFragment.newInstance(category.name, category.totalReviws)
            fragmentList.add(fragment)
        }

        binding.tbItemList.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val selectedFragment = fragmentList[tab.position]
                replaceFragment(selectedFragment)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        val initialFragment = fragmentList[INITIAL_FRAGMENT_INDEX]
        replaceFragment(initialFragment)

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = childFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }



}