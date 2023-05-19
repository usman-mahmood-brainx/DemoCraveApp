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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)

        val categoryList = listOf(
            Category("Brunch", 460, ContextCompat.getColor(requireContext(), R.color.brunch)),
            Category("Burgers", 61, ContextCompat.getColor(requireContext(), R.color.burgers)),
            Category("Pizza", 46, ContextCompat.getColor(requireContext(), R.color.pizza)),
            Category("Bangels", 28, ContextCompat.getColor(requireContext(), R.color.bangels)),
            Category("Tacos", 19, ContextCompat.getColor(requireContext(), R.color.tacos)),
            Category("Donuts", 17, ContextCompat.getColor(requireContext(), R.color.donuts)),
            Category("Coffee", 13, ContextCompat.getColor(requireContext(), R.color.coffe)),
            Category("Bar", 10, ContextCompat.getColor(requireContext(), R.color.bar))
        )

        pieChartSetup(categoryList)
        reviewsListSetup(categoryList)
        dynamicTabsSetup(categoryList)


        return binding.root

    }


    private fun reviewsListSetup(categoryList: List<Category>) {
        val categoryAdapter = CategoryAdapter(categoryList)
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private fun pieChartSetup(categoryList: List<Category>) {


        val sortedCategoryList = categoryList.sortedByDescending { it.totalReviws }
        var sum = 0
        val pieEntries = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>()

        for (category in sortedCategoryList) {
            pieEntries.add(PieEntry(category.totalReviws.toFloat(), category.name))
            colors.add(category.color)
            sum += category.totalReviws
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.colors = colors
        dataSet.sliceSpace = 1f

        val data = PieData(dataSet)
        data.setValueTextSize(0f)
        data.setValueTextColor(Color.WHITE)

        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setDrawEntryLabels(false)
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.isRotationEnabled = false
        binding.pieChart.setTouchEnabled(false)
        binding.pieChart.setHoleRadius(75f)

        binding.pieChart.animateY(1000, Easing.EaseInOutQuad)
        binding.pieChart.invalidate()

        binding.tvSum.text = "$sum"

    }


    private fun dynamicTabsSetup(categoryList: List<Category>) {
//        val dynamicFragmentList = categoryList.map {
//            DynamicFragment.newInstance(it.name,it.totalReviws)
//        }
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

        val initialFragment = fragmentList[0]
        replaceFragment(initialFragment)

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = childFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
    


}